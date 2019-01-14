package com.tk.monitor.tkserver.es.util;

import com.alibaba.fastjson.JSONObject;
import com.tk.monitor.tkserver.entity.Message;
import com.tk.monitor.tkserver.es.config.ESConfig;
import com.tk.monitor.tkserver.message.MessageEnum;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Component
public class BulkProcessorUtil {
    private static final Logger log = LoggerFactory.getLogger(BulkProcessorUtil.class);
    private static TransportClient transportClient;
    private static volatile BulkProcessor processor;


    @Autowired
    public BulkProcessorUtil(TransportClient transportClient) {
        System.out.println(transportClient.nodeName());
        this.transportClient = transportClient;
    }


    /**
     * 获取processor
     * @return
     */
    private static BulkProcessor getProcessor() {
        if (Objects.isNull(processor)) {
            synchronized (BulkProcessorUtil.class) {
                if (Objects.isNull(processor)) {
                    processor = createBuilder();
                }
            }
        }
        return processor;
    }


    /**
     * 创建processor
     * @return
     */
    private static BulkProcessor createBuilder() {
        BulkProcessor.Builder processor = BulkProcessor.builder(transportClient, new BulkProcessor.Listener() {

            //调用bulk之前执行 ，例如你可以通过request.numberOfActions()方法知道numberOfActions
            @Override
            public void beforeBulk(long executionId, BulkRequest request) {
                log.info("es的批量插入开始,data={}",new Date());

            }

            //调用bulk之后执行 ，例如你可以通过request.hasFailures()方法知道是否执行失败
            @Override
            public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
                log.info("es的批量插入完成,结果：{},data={}",response.hasFailures(),new Date());
            }

            //调用失败
            @Override
            public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
                log.error("es的调用失败{}",failure.getMessage());
            }
        });
        BulkProcessor bulkProcessor = processor.setBulkActions(1000)//每1000次请求
                .setBulkSize(new ByteSizeValue(5, ByteSizeUnit.MB))//拆成5MB一次
                .setFlushInterval(TimeValue.timeValueSeconds(5))//无论请求数多小，5s执行一次
                .setConcurrentRequests(1)//设置可以允许并发的请求数
                .setBackoffPolicy(
                        //设置自定义重复请求机制，最开始等待100毫秒，之后成倍更加，重试3次，当一次或多次重复请求失败后因为计算资源不够抛出 EsRejectedExecutionException 异常，可以通过BackoffPolicy.noBackoff()方法关闭重试机制
                        BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3)
                ).build();

        return bulkProcessor;
    }

    /**
     * 插入数据
     * @param message
     */
    public static void insertData(Message message, MessageEnum type) {
        log.info("插入数据：data={}",message);
        BulkProcessor processor = getProcessor();
        String data = JSONObject.toJSONString(message);
        Map map = JSONObject.parseObject(data, Map.class);
        if (MessageEnum.URL.equals(type)) {
            processor.add(ESConfig.newURLRequest(map));
        } else if (MessageEnum.CONNECT.equals(type)) {
            processor.add(ESConfig.newConnectRequest(map));
        }
    }

}

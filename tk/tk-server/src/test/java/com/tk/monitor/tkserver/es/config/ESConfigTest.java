package com.tk.monitor.tkserver.es.config;

import com.alibaba.fastjson.JSONObject;
import com.tk.monitor.tkserver.entity.ClientInfo;
import com.tk.monitor.tkserver.entity.Message;
import com.tk.monitor.tkserver.entity.messageLogData.UrlLogData;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ESConfigTest {


//    @Autowired
//    private ESConfig esConfig;
////    @Autowired
////    private ElasticsearchTemplate elasticsearchTemplate;
////    @Autowired
////    private ElasticsearchProperties elasticsearchProperties;
//
//
//    @Test
//    public void client() {
//        try {
//            TransportClient client = esConfig.client();
//            CreateIndexRequest createIndexRequest = new CreateIndexRequest("test6.4.3");
//            client.admin().indices().create(createIndexRequest);
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
//    }


//    @Test
//    public void putData() {
//        try {
//            TransportClient client = esConfig.client();
//
//            Map<String, Object> jsonMap = new HashMap();
//            long longTime = new Date().getTime() - 123;
//            jsonMap.put("time", longTime);
//            jsonMap.put("startTime", new Date().getTime());
//            jsonMap.put("endTime", new Date());
//            jsonMap.put("url", "wwww");
//            jsonMap.put("reason", "reason");
//            jsonMap.put("result", "result");
//            String json = JSONObject.toJSONString(jsonMap);
//
//            //创建对象
//            Message message = new Message();
//            ClientInfo clientInfo = new ClientInfo();
//            clientInfo.setIp("10.10.0.111");
//            clientInfo.setClientName("秒杀系统-1");
//            clientInfo.setConTime(new Date());
//            UrlLogData urlLogData = new UrlLogData();
//            urlLogData.setTime(123);
//            urlLogData.setEndTime(new Date());
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            urlLogData.setStartTime(new Date());
//            urlLogData.setUrl(json);
//            message.setClientInfo(clientInfo);
//            message.setMessageLogData(urlLogData);
//            String data = JSONObject.toJSONString(message);
//            Map map = JSONObject.parseObject(data, Map.class);
//            IndexResponse indexResponse = client.prepareIndex("url_log1", "log")
//                    .setSource(map)
//                    .get();
//            System.out.println(indexResponse.toString());
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void cheangeData() {
//        Map<String, Object> jsonMap = new HashMap();
//        long longTime = new Date().getTime() - 123;
//        jsonMap.put("time", longTime);
//        jsonMap.put("startTime", new Date().getTime());
//        jsonMap.put("endTime", new Date());
//        jsonMap.put("url", "wwww");
//        jsonMap.put("reason", "reason");
//        jsonMap.put("result", "result");
//        String json = JSONObject.toJSONString(jsonMap);
//        System.out.println(json);
//        UrlLogData urlLogData = JSONObject.parseObject(json, UrlLogData.class);
//        System.out.println(urlLogData);
//
//    }
}
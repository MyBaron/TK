package com.tk.monitor.tkserver.message;

import com.alibaba.fastjson.JSONObject;
import com.tk.monitor.tkserver.entity.ClientInfo;
import com.tk.monitor.tkserver.entity.Message;
import com.tk.monitor.tkserver.entity.MessageLogData;
import com.tk.monitor.tkserver.entity.messageLogData.UrlLogData;
import com.tk.monitor.tkserver.es.util.BulkProcessorUtil;
import com.tk.monitor.tkserver.tknetty.client.ClientManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class MessageManager {
    private static final Logger log = LoggerFactory.getLogger(MessageManager.class);

    /**
     * 客户端日志消息队列
     */
    private static BlockingQueue<Message> MessageBlockList = new ArrayBlockingQueue<>(2000);

    public static BlockingQueue<Message> getMessageBlockList() {
        return MessageBlockList;
    }

    /**
     * 创建消息
     * @param id
     */
    public static void messageBuild(String id,MessageVO.Body.Content content) {
        Message message = new Message();
        //获取到ClientInfo
        ClientInfo clientInfo = ClientManager.getRegistClientList().get(id);
        if (Objects.nonNull(clientInfo)) {
            //获取信息类型
            message.setType(content.getType());
            //获取信息属性
            message.setProperty(content.getProperty());
            //获取主体信息
            String contentJson = content.getContent();
            //todo
            MessageLogData messageLogData=null;
            if (ContentEnum.URL.getType().equals(content.getType())) {
                messageLogData = JSONObject.parseObject(contentJson, UrlLogData.class);
            }
            message.setClientInfo(clientInfo);
            message.setMessageLogData(messageLogData);
            BulkProcessorUtil.insertData(message,MessageEnum.URL);
        }else {
            log.error("clientInfo为空，id为{}",id);
        }
    }

    public static void messageBuild(ClientInfo clientInfo,Message message) {

    }
}

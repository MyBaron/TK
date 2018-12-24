package com.tk.monitor.tkserver.message;

import com.tk.monitor.tkserver.entity.ClientInfo;
import com.tk.monitor.tkserver.entity.Message;
import com.tk.monitor.tkserver.entity.MessageLogData;
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
     * @param messageLogData
     */
    public static void messageBuild(String id,MessageLogData messageLogData) {
        Message message = new Message();
        ClientInfo clientInfo = ClientManager.getRegistClientList().get(id);
        if (Objects.nonNull(clientInfo)) {
            message.setClientInfo(clientInfo);
            message.setMessageLogData(messageLogData);
            try {
                //提交到
                if (!MessageBlockList.offer(message, 5, TimeUnit.SECONDS)){
                    log.error("客户端日志消息队列已满，消息丢失。message= {}",message);
                }
            } catch (InterruptedException e) {
                log.error("放到客户端日志消息队列发送错误",e);
            }
        }else {
            log.error("clientInfo为空，id为{}",id);
        }
    }
}

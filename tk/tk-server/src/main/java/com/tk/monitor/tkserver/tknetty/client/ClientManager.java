package com.tk.monitor.tkserver.tknetty.client;

import com.tk.monitor.tkserver.entity.ClientInfo;
import com.tk.monitor.tkserver.entity.Message;
import com.tk.monitor.tkserver.es.util.BulkProcessorUtil;
import com.tk.monitor.tkserver.message.ContentEnum;
import com.tk.monitor.tkserver.message.MessageEnum;
import com.tk.monitor.tkserver.message.MessageManager;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;

public class ClientManager {
    private static final Logger log = LoggerFactory.getLogger(ClientManager.class);

    /**
     * 客户端注册队列
     * k-v
     * k: 生成的唯一Id,与channel绑定
     * v: 客户端信息
     */
    public static ConcurrentHashMap<String,ClientInfo> registClientList = new ConcurrentHashMap<>();

    public static AttributeKey<String> attrClientId = AttributeKey.newInstance("clientId");

    /**
     * 客户端断开连接队列
     */
    public static DelayQueue<ClientInfo> deadClientQueue = new DelayQueue<>();


    public static ConcurrentHashMap<String, ClientInfo> getRegistClientList() {
        return registClientList;
    }

    public static void setRegistClientList(ConcurrentHashMap<String, ClientInfo> registClientList) {
        ClientManager.registClientList = registClientList;
    }


    /**
     * 放进客户端注册队列
     * @param clientInfo
     */
    public static String putRegistList(ClientInfo clientInfo) {
        String clientId;
        //从断开队列中移除重新连接的客户度
        ClientInfo breakClient;
        if (Objects.nonNull(breakClient=hasDeadQueue(clientInfo))) {
            log.info("重新连接的客户端：{}", breakClient);
            clientId=breakClient.getId();
            //放入es消息队列
            clientInfo.setConTime(new Date());
            Message message = new Message();
            message.setType(ContentEnum.RESTCONNECT.getType());
            message.setClientInfo(clientInfo);
            message.setProperty("info");
            BulkProcessorUtil.insertData(message, MessageEnum.CONNECT);
        }else {
            clientId = randomStr();
        }
        clientInfo.setId(clientId);
        registClientList.put(clientId, clientInfo);
        return clientId;
    }

    /**
     * 获取客户端数据
     * @param key
     * @return
     */
    public static ClientInfo getClientInfo(String key) {
        if (Objects.isNull(key)) {
            return null;
        }
        return registClientList.get(key);
    }
    /**
     * 放进客户端断开队列
     * @param clientInfo
     */
    public static void putDeadQueue(ClientInfo clientInfo) {
        deadClientQueue.add(clientInfo);
        Message message = new Message();
        message.setClientInfo(clientInfo);

    }

    /**
     * 判断是否存在断开队列中，并移除
     * 用于客户端重连后移出断开队列
     * @param clientInfo
     * @return
     */
    public static boolean removeDeadQueue(ClientInfo clientInfo) {
        return deadClientQueue.remove(clientInfo);
    }


    public static ClientInfo hasDeadQueue(ClientInfo clientInfo) {
        Iterator<ClientInfo> iterator = deadClientQueue.iterator();
        while (iterator.hasNext()) {
            ClientInfo breakClient = iterator.next();
            if (breakClient.equals(clientInfo)) {
                //重连，移出队列
                removeDeadQueue(clientInfo);
                return breakClient;
            }
        }
        return null;
    }

    /**
     * 获取断开超时的客户端
     * @return
     */
    public static ClientInfo getDeadClient() {
        try {
            return deadClientQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成随机的唯一clientId
     * @return
     */
    public static String randomStr() {
        StringBuilder result=new StringBuilder();
        for(int i=0;i<6;i++){
            int intVal=(int)(Math.random()*26+97);
            result.append((char)intVal);
        }
        //如果已经存在，那么就再创建一个
        if (registClientList.containsKey(result.toString())) {
            return randomStr();
        }else {
            return result.toString();
        }
    }

}

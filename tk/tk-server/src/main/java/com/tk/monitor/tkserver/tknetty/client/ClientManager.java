package com.tk.monitor.tkserver.tknetty.client;

import com.tk.monitor.tkserver.entity.ClientInfo;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    /**
     * 放进客户端注册队列
     * @param clientInfo
     */
    public static String putRegistList(ClientInfo clientInfo) {
        String clientId = randomStr();
        registClientList.put(clientId, clientInfo);
        //从断开队列中移除重新连接的客户度
        if (removeDeadQueue(clientInfo)) {
            log.info("重新连接的客户端：{}",clientInfo);
        }
        return clientId;
    }

    public static ConcurrentHashMap<String, ClientInfo> getRegistClientList() {
        return registClientList;
    }

    public static void setRegistClientList(ConcurrentHashMap<String, ClientInfo> registClientList) {
        ClientManager.registClientList = registClientList;
    }

    /**
     * 放进客户端断开队列
     * @param clientInfo
     */
    public static void putDeadQueue(ClientInfo clientInfo) {
        deadClientQueue.add(clientInfo);
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

package com.tk.monitor.tkserver.entity;


import org.springframework.data.annotation.Id;

import java.lang.annotation.Documented;
import java.util.Date;

/**
 * 保存客户端监控信息的数据
 */

public class Message {
    /**
     * 客户端的数据
     */
    private ClientInfo clientInfo;

    /**
     * 数据类型 URL,heart,JVM....
     */
    private String type;

    /**
     * 消息的属性 Info,error...
     */
    private String property;

    /**
     * 消息创建时间
     */
    private Date time;
    /**
     * 数据信息
     */
    private MessageLogData messageLogData;

    public Message() {
        this.time = new Date();
    }
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public ClientInfo getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }

    public MessageLogData getMessageLogData() {
        return messageLogData;
    }

    public void setMessageLogData(MessageLogData messageLogData) {
        this.messageLogData = messageLogData;
    }

    @Override
    public String toString() {
        return "Message{" +
                "clientInfo=" + clientInfo +
                ", type='" + type + '\'' +
                ", property='" + property + '\'' +
                ", messageLogData=" + messageLogData +
                '}';
    }
}

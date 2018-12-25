package com.tk.monitor.tkserver.entity;


import org.springframework.data.annotation.Id;

import java.lang.annotation.Documented;

/**
 * 保存客户端监控信息的数据
 */

public class Message {
    /**
     * 客户端的数据
     */
    private ClientInfo clientInfo;

    /**
     * 数据信息
     */
    private MessageLogData messageLogData;

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
}

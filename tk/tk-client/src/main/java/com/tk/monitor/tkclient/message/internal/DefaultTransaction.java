package com.tk.monitor.tkclient.message.internal;

import com.alibaba.fastjson.JSONObject;
import com.tk.monitor.tkclient.message.MessageBody;
import com.tk.monitor.tkclient.message.MessageBuild;
import com.tk.monitor.tkclient.message.Transaction;
import com.tk.monitor.tkclient.message.tknetty.Client.ClientHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DefaultTransaction implements Transaction {
    private static final Logger log = LoggerFactory.getLogger(DefaultTransaction.class);

    private Date startTime;
    private Date endTime;
    //类型
    private String type;
    //URL
    private String URL;
    //信息的属性 info,error
    private String property;


    public DefaultTransaction() {
    }

    public DefaultTransaction(Date startTime, String type, String URL) {
        this.startTime = startTime;
        this.type = type;
        this.URL = URL;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        type = type;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        property = property;
    }

    @Override
    public void complete(String property,String reason) {
        //放到List<messageBody>中
        //统计时间
        Map<String, Object> jsonMap = new HashMap();
        MessageBody messageBody = new MessageBody();
        long longTime = new Date().getTime() - startTime.getTime();
        endTime = new Date();
        jsonMap.put("time", longTime);
        jsonMap.put("startTime", startTime.getTime());
        jsonMap.put("endTime", endTime);
        jsonMap.put("url", URL);
        jsonMap.put("reason", reason);
        messageBody.setType(type);
        messageBody.setProperty(property);
        messageBody.setContent(JSONObject.toJSONString(jsonMap));
        ClientHandler.sendMessage(messageBody);
    }

}

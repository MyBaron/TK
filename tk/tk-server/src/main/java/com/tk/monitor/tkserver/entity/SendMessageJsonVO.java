package com.tk.monitor.tkserver.entity;

import java.util.Map;

public class SendMessageJsonVO {

    //延迟发送时间
    private long waitTime;
    //忽略发送的应用 用,隔开
    private String ignoreCode;
    //
    private Map<String, String> agentList;


    public long getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(long waitTime) {
        this.waitTime = waitTime;
    }

    public String getIgnoreCode() {
        return ignoreCode;
    }

    public void setIgnoreCode(String ignoreCode) {
        this.ignoreCode = ignoreCode;
    }

    public Map<String, String> getAgentList() {
        return agentList;
    }

    public void setAgentList(Map<String, String> agentList) {

        this.agentList = agentList;
    }

    @Override
    public String toString() {
        return "SendMessageJsonVO{" +
                "waitTime=" + waitTime +
                ", ignoreCode='" + ignoreCode + '\'' +
                ", agentList=" + agentList +
                '}';
    }
}

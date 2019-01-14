package com.tk.monitor.tkserver.entity.messageLogData;

import com.alibaba.fastjson.annotation.JSONField;
import com.tk.monitor.tkserver.entity.MessageLogData;

import java.util.Date;

public class UrlLogData implements MessageLogData {
    private long time;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    private String url;
    private String reason;


    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "UrlLogData{" +
                "time=" + time +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", url='" + url + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}

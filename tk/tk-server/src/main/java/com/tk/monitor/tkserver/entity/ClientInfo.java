package com.tk.monitor.tkserver.entity;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class ClientInfo  implements Delayed {


    /**
     *  channelId
     */
    private String id;

    /**
     * 客户端名称
     */
    private String ClientName;

    /**
     * 客户端ip
     */
    private String ip;

    /**
     * 连接时间
     */
    private Date conTime;

    /**
     * 断线时间
     */
    private Date breakTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientName() {
        return ClientName;
    }

    public void setClientName(String clientName) {
        ClientName = clientName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getConTime() {
        return conTime;
    }

    public void setConTime(Date conTime) {
        this.conTime = conTime;
    }

    public Date getBreakTime() {
        return breakTime;
    }

    public void setBreakTime(Date breakTime) {
        this.breakTime = breakTime;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        //一分钟的过期时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(this.breakTime);
        calendar.add(Calendar.SECOND,4);
        long timeInMillis = calendar.getTimeInMillis();
        return unit.convert(timeInMillis-new Date().getTime(),TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return (int)(o.getDelay(TimeUnit.MICROSECONDS)-this.getDelay(TimeUnit.MICROSECONDS));
    }


    @Override
    public boolean equals(Object o) {
        if (o instanceof ClientInfo) {
            ClientInfo c = (ClientInfo) o;
            if (c.ClientName.equals(this.ClientName)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public String toString() {
        return "ClientInfo{" +
                "ClientName='" + ClientName + '\'' +
                ", ip='" + ip + '\'' +
                ", conTime=" + conTime +
                ", breakTime=" + breakTime +
                '}';
    }
}

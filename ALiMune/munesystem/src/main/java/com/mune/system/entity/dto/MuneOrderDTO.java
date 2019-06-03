package com.mune.system.entity.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

public class MuneOrderDTO {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long orderId;
    private long totalMoney;
    private long tableNumber;
    private long createTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String orderDate;


    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(long totalMoney) {
        this.totalMoney = totalMoney;
    }

    public long getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(long tableNumber) {
        this.tableNumber = tableNumber;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        return "MuneOrderDTO{" +
                "orderId=" + orderId +
                ", totalMoney=" + totalMoney +
                ", tableNumber=" + tableNumber +
                ", createTime=" + createTime +
                ", orderDate='" + orderDate + '\'' +
                '}';
    }
}

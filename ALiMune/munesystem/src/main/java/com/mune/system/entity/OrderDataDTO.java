package com.mune.system.entity;

import java.util.List;

public class OrderDataDTO {

    private List<OrderDTO>  orderData;

    private int table;

    public int getTable() {
        return table;
    }

    public void setTable(int table) {
        this.table = table;
    }


    public List<OrderDTO> getOrderData() {
        return orderData;
    }

    public void setOrderData(List<OrderDTO> orderData) {
        this.orderData = orderData;
    }

    @Override
    public String toString() {
        return "OrderDataDTO{" +
                "orderData=" + orderData +
                ", table=" + table +
                '}';
    }
}

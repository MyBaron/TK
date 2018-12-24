package com.tk.monitor.tkclient.entity;

public class Client {

    private String clientName;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @Override
    public String toString() {
        return "client{" +
                "clientName='" + clientName + '\'' +
                '}';
    }
}

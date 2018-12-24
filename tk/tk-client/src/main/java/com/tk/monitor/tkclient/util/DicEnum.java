package com.tk.monitor.tkclient.util;

public enum DicEnum {
    heart("heart"),
    client("client"),
    PROPERTY_INFO("info"),
    PROPERTY_ERROR("error");

    private String type;

    DicEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}

package com.tk.monitor.tkserver.message;

public enum ContentEnum {
    URL("URL"),
    Heart("heart"),
    CONNECT("connect"),
    BACKCONNECT("backConnect"),
    RESTCONNECT("restConnect");


    private String type;

    ContentEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}

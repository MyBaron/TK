package com.mune.system.config.code;

/**
 * webSocket的指令
 * 指令范围1000-1099
 */
public enum WebSocketEnum {
    FLASH_ORDER_LIST(1001); //刷新订单列表


    private int code;

    WebSocketEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

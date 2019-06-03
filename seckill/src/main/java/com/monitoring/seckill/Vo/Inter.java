package com.monitoring.seckill.Vo;

public interface Inter {

    void test();


    default void say(){
        System.out.println("这是接口");
    }

}

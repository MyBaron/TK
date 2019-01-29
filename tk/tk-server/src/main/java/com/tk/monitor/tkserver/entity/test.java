package com.tk.monitor.tkserver.entity;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;

import java.util.*;
import java.util.stream.Collectors;

public class test {

    public static void main(String[] args) {
        SendMessageJsonVO v1 = new SendMessageJsonVO();
        SendMessageJsonVO v2 = new SendMessageJsonVO();
        SendMessageJsonVO v3 = new SendMessageJsonVO();
//        v1.setIgnoreCode("v1");
//        v2.setIgnoreCode("v2");
//        v3.setIgnoreCode("v3");
        List<SendMessageJsonVO> listVo = new ArrayList<>();
//        listVo.add(v1);
//        listVo.add(v2);
//        listVo.add(v3);
        List<String> collect = listVo.stream().map(s -> s.getIgnoreCode()).collect(Collectors.toList());
        System.out.println(collect.isEmpty());
        System.out.println(Joiner.on('|').join(collect));
    }
}

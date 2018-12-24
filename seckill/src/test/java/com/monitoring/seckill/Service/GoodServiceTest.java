package com.monitoring.seckill.Service;

import com.alibaba.fastjson.JSONObject;
import com.monitoring.seckill.Entity.MonGood;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.print.attribute.HashAttributeSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodServiceTest {


    @Autowired
    public GoodService goodService;

    @Test
    public void checkGoodAll() {
        List<MonGood> monGoods = goodService.checkGoodAll();
        System.out.println(monGoods);
    }

    @Test
    public void saveGood() {
        List<String> filePathList = new ArrayList<>();
        filePathList.add("123");
        filePathList.add("123");
        filePathList.add("123");
        filePathList.add("123");
        filePathList.add("123");
        System.out.println(filePathList);
        Map<String, Object> map = new HashMap<>();
        map.put("fileMap", filePathList);
        System.out.println( JSONObject.toJSONString(map));
    }
}
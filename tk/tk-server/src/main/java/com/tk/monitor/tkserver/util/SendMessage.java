package com.tk.monitor.tkserver.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SendMessage {

    private static String url;
    private static String secret;
    @Value("${secret}")
    public  void setSecret(String secret) {
        SendMessage.secret = secret;
    }

    @Value("${tk.email.url}")
    public  void setAddress(String url) {
        SendMessage.url = url;
    }

    //发送邮件
    public static void send(String mgs,String subject){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("mgs", mgs);
        body.add("subject", subject);
        body.add("secret",secret);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(body, httpHeaders);
        restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
    }



    public static final String msg = "<table border=\"1\"  bordercolor=\"black\" cellspacing=\"0px\" cellpadding=\"4px\">\n" +
            "<tr >\n" +
            "<td>告警主机</td>\n" +
            "<td bgcolor=\"#FF3333\">%s</td>\n" +   //ip
            "</tr>\n" +
            "<tr>\n" +
            "<td>告警时间</td>\n" +
            "<td>%s</td>\n" +   //date
            "</tr>\n" +
            "<tr>\n" +
            "<td>告警等级</td>\n" +
            "<td>%s</td>\n" +      //等级
            "</tr>\n" +
            "<tr>\n" +
            "<td>告警信息</td>\n" +
            "<td>%s</td>\n" +    //告警信息
            "</tr>\n" +
            "<tr>\n" +
            "<td>告警项目</td>\n" +
            "<td>%s</td>\n" +     //项目
            "</tr>\n" +
            "<tr >\n" +
            "<td>问题详情</td>\n" +
            "<td bgcolor=\"#FF3333\">%s:&nbsp;%s</td>\n" +   //问题 详情
            "</tr>\n" +
            "<tr>\n" +
            "<td>当前状态</td>\n" +
            "<td>%s:&nbsp;%s</td>\n" +        // key  value
            "</tr>\n" +
            "<tr>\n" +
            "<td>事件ID</td>\n" +
            "<td>%s</td>\n" +             //事件id
            "</tr>\n" +
            "</table>";
}

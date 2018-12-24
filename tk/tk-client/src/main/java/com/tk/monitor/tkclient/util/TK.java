package com.tk.monitor.tkclient.util;

import com.tk.monitor.tkclient.config.KTConfig;
import com.tk.monitor.tkclient.entity.Client;
import com.tk.monitor.tkclient.message.Transaction;
import com.tk.monitor.tkclient.message.internal.DefaultTransaction;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TK {

    private static final Logger log = LoggerFactory.getLogger(TK.class);
    public static final KTConfig ktConfig = new KTConfig();

    public static boolean initialize() {
        //获取初始化地址
        String address = PropertiesUtil.getProperty("TK.server.address");
        int port = Integer.valueOf(PropertiesUtil.getProperty("TK.server.port", "8080"));
        if (StringUtils.isEmpty(address)) {
            log.error("KT address 为Null");
            return false;
        }
        String clientName = PropertiesUtil.getProperty("TK.client.name", UUID.randomUUID().toString());
        ktConfig.setClientName(clientName);
        ktConfig.setAddress(address);
        ktConfig.setPort(port);
        log.info("远程服务器地址：{}，端口：{}",address,port);

        //开始连接远程服务器
        //启动一个线程去启动线程
        ExecutorService executorService =
                Executors.newSingleThreadExecutor();
        return true;
    }

    /**
     * 创建客户端信息
     * @return
     */
    public static Client clientBuild() {
        String clientName = ktConfig.getClientName();
        if (StringUtils.isEmpty(clientName)) {
            initialize();
            clientName = ktConfig.getClientName();
        }
        Client client = new Client();
        client.setClientName(clientName);
        return client;
    }


    //创建数据记录实例
    public static Transaction newTransaction(String type,String URL) {
        return new DefaultTransaction(new Date(), type, URL);
    }


    public static void main(String[] args) {
        initialize();
    }
}


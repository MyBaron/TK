package com.tk.monitor.tkserver.tknetty.client;

import com.tk.monitor.tkserver.entity.ClientInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckClientThread implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(CheckClientThread.class);


    @Override
    public void run() {
        while (true) {
            log.info("阻塞获取超时的客户端");
            ClientInfo deadClient = ClientManager.getDeadClient();
            log.info("超时断开连接的客户端：{}",deadClient.toString());
        }
    }
}

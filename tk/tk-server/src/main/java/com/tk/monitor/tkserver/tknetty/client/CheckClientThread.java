package com.tk.monitor.tkserver.tknetty.client;

import com.tk.monitor.tkserver.entity.ClientInfo;
import com.tk.monitor.tkserver.util.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckClientThread implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(CheckClientThread.class);


    @Override
    public void run() {
        while (true) {
            log.info("阻塞获取超时的客户端");
            ClientInfo deadClient = ClientManager.getDeadClient();
            log.info("超时断开连接的客户端：{},将发送告警邮件",deadClient.toString());
            String format = String.format(SendMessage.msg, deadClient.getIp(),
                    deadClient.getBreakTime(), "严重","服务器超时未连接", deadClient.getClientName(),
                    "服务器故障", "服务器断开连接",
                    "服务器状态", "超时未连接成功",
                    ClientManager.randomStr());
            SendMessage.send(format,"告警：服务器重连超时");
        }
    }
}

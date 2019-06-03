package com.tk.monitor.tkserver;


import com.tk.monitor.tkserver.entity.ClientInfo;
import com.tk.monitor.tkserver.tknetty.client.ClientManager;
import com.tk.monitor.tkserver.util.SendMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class emailTest {

    @Test
    public void sendMessage() {
//        ClientInfo deadClient = new ClientInfo();
//        deadClient.setIp("10.12.201.23");
//        deadClient.setBreakTime(new Date());
//        deadClient.setClientName("doi抢购服务器001");
//        String format = String.format(SendMessage.msg, deadClient.getIp(),
//                deadClient.getBreakTime(), "严重", "服务器超时未连接",deadClient.getClientName(),
//                "服务器故障", "服务器断开连接",
//                "服务器状态", "超时未连接成功",
//                ClientManager.randomStr());
//        SendMessage.send(format,"告警：doi服务器故障");
    }
}

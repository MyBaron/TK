package com.mune.system.websocket;


import com.mune.system.utils.JsonUtil;
import com.mune.system.utils.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint("/websocket")
@Slf4j
//此注解相当于设置访问URL
public class WebSocket {

    private Session session;

    private static CopyOnWriteArraySet<WebSocket> webSockets =new CopyOnWriteArraySet<>();
    //todo单点发送消息

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSockets.add(this);
        log.info("【websocket消息】有新的连接，总数为:"+webSockets.size());
    }

    @OnClose
    public void onClose() {
        webSockets.remove(this);
        log.info("【websocket消息】连接断开，总数为:"+webSockets.size());
    }

    @OnMessage
    public void onMessage(String message) {
        log.info("【websocket消息】收到客户端消息:"+message);
    }

    // 此为广播消息
    public static void sendAllMessage(int code,String message,Object data) {
        message= JsonUtil.obj2jsonByFeatures(ResultVO.success(code,message,data));
        for(WebSocket webSocket : webSockets) {
            log.info("【websocket消息】广播消息:"+message);
            try {
                webSocket.session.getAsyncRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }




}

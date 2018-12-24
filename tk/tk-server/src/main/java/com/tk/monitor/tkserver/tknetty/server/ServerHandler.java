package com.tk.monitor.tkserver.tknetty.server;

import com.alibaba.fastjson.JSONObject;
import com.tk.monitor.tkserver.entity.ClientInfo;
import com.tk.monitor.tkserver.message.MessageVO;
import com.tk.monitor.tkserver.tknetty.client.ClientManager;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ServerHandler extends SimpleChannelInboundHandler<MessageVO.Message> {
    private static final Logger log = LoggerFactory.getLogger(ServerHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageVO.Message msg) throws Exception {
        if (MessageVO.Type.heart.equals(msg.getType())) {
            System.out.println("this is heart");
            prient(msg);
        }else if(MessageVO.Type.client.equals(msg.getType())){
            createClientInfo(msg, ctx.channel());
        }else if (MessageVO.Type.monitor_command.equals(msg.getType())) {
            System.out.println("this is monitor");
            prient(msg);
        }
    }

    /**
     * 客户端连接
     * @param msg
     * @param channel
     */
    private void createClientInfo(MessageVO.Message msg, Channel channel) {
        List<MessageVO.Body.Content> contentList =
                msg.getBody().getContentList();
        ClientInfo clientInfo = new ClientInfo();
        //正常只有一条消息
        MessageVO.Body.Content content = contentList.get(0);
        if (Objects.nonNull(content)) {
            clientInfo.setConTime(new Date());
            clientInfo.setClientName(
                    JSONObject.parseObject(content.getContent())
                    .getString("clientName"));
            clientInfo.setIp(channel.remoteAddress().toString());
        }
        //放到注册队列中
        String clientId = ClientManager.putRegistList(clientInfo);
        //给这个管道绑定一个clientId
        channel.attr(ClientManager.attrClientId).set(clientId);
        log.info("注册成功-----clientId：{},clientName：{},当前注册的客户端个数为：{}",clientId,clientInfo.getClientName(),
                ClientManager.registClientList.size());
    }

    private void prient(MessageVO.Message msg) {
        List<MessageVO.Body.Content> contentList =
                msg.getBody().getContentList();
        for (MessageVO.Body.Content c : contentList) {
            try {
                System.out.println(new String(c.toByteArray(),"utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        //判断心跳类型
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            String type = "";
            if (event.state() == IdleState.READER_IDLE) {
                //读
                type = "this is read idle";
            } else if (event.state() == IdleState.WRITER_IDLE) {
                //写
                type = "this is write idle";
            } else if (event.state() == IdleState.ALL_IDLE) {
                type = "this is all idle";
                //进行channel关闭
                ctx.channel().close();
            }
            System.out.println( ctx.channel().remoteAddress()+"超时类型：" + type);
        }else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端断开了连接");
        //将客户端添加到断开连接队列
        Object client = ctx.channel().attr(ClientManager.attrClientId).get();
        if (Objects.isNull(client)) {
            log.error("clientId 为空,IP：{}",ctx.channel().remoteAddress().toString());
        }
        ClientInfo clientInfo = ClientManager.registClientList.get(client.toString());
        ClientManager.registClientList.remove(client);
        //设置断线时间
        clientInfo.setBreakTime(new Date());
        ClientManager.putDeadQueue(clientInfo);
        log.info("将客户端放进了断开连接队列：{}",clientInfo.toString());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("连接的客户端地址：{}" ,ctx.channel().remoteAddress());
        super.channelActive(ctx);
    }
}

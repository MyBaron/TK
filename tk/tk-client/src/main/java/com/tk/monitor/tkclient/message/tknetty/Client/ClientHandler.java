package com.tk.monitor.tkclient.message.tknetty.Client;

import com.alibaba.fastjson.JSONObject;
import com.tk.monitor.tkclient.entity.Client;
import com.tk.monitor.tkclient.message.MessageBody;
import com.tk.monitor.tkclient.message.MessageBuild;
import com.tk.monitor.tkclient.message.MessageSendThread;
import com.tk.monitor.tkclient.message.MessageVO;
import com.tk.monitor.tkclient.util.DicEnum;
import com.tk.monitor.tkclient.util.TK;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ClientHandler extends SimpleChannelInboundHandler<Object> {
    private static final Logger log = LoggerFactory.getLogger(ClientHandler.class);
    private static AtomicInteger fcount = new AtomicInteger();
    private static ExecutorService executorService = Executors.newSingleThreadExecutor();
    private static Future<?> submit;

    private static volatile ChannelHandlerContext context;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
    }


    public static void sendMessage(MessageBody messageBody) {
        if (Objects.nonNull(context)) {
            MessageVO.Message build = MessageBuild.build(messageBody);
            context.writeAndFlush(build);
        }else {
            log.error("断开连接，消息未能发送");
        }
    }

    /**
     * 建立连接时
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("建立连接时：" + new Date());
        //第一次连接 发送client注册
        context = ctx;
        MessageBody messageBody = new MessageBody();
        messageBody.setType(DicEnum.client.getType());
        messageBody.setProperty("client");
        messageBody.setContent(JSONObject.toJSONString(TK.clientBuild()));
        MessageVO.Message clientMsg = MessageBuild.buildLogion(messageBody);
        ctx.writeAndFlush(clientMsg);
//        submit = executorService.submit(new MessageSendThread(ctx));
    }

    /**
     * 关闭连接时
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("关闭连接时：" + new Date());
//        submit.cancel(false);
        context = null;
        DoConnection.doConnection(new Bootstrap(),ctx.channel().eventLoop());
        super.channelInactive(ctx);
    }


    /**
     * 发送心跳
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (IdleState.WRITER_IDLE.equals(event.state())) { // 如果写通道处于空闲状态,就发送心跳命令
                log.info("循环请求的时间(发送心跳)：" + new Date() + "，次数" + fcount.incrementAndGet());
                ctx.writeAndFlush(MessageBuild.buildHeart());
            }
        }

    }

}

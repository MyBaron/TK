package com.tk.monitor.tkclient.message;


import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;


public class MessageSendThread implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(MessageSendThread.class);

    private ChannelHandlerContext ctx;

    public MessageSendThread(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    /**
     * todo 等待测试，当断开连接后，channel是否可写
     */
    @Override
    public void run() {
        while (true) {
            System.out.println("阻塞等待");
            MessageBody messageBody = null;
            try {
                messageBody = MessageBuild.messageBodies.take();
            } catch (InterruptedException e) {
                log.error("MessageBody 信息队列中断");
                e.printStackTrace();
            }
            MessageVO.Message build = MessageBuild.build(messageBody);
            if (ctx.channel().isActive()) {
                log.info("MessageSendThread send.....");
                System.out.println(MessageBuild.messageBodies.size());
                ctx.writeAndFlush(build);
            }else {
                log.error("MessageSendThread send.....error");
                MessageBuild.messageBodies.offer(messageBody);
            }

        }

    }
}

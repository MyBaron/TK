package com.tk.monitor.tkclient.message.tknetty.Client;

import com.tk.monitor.tkclient.util.TK;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;


public class DoConnection {

    private static Logger log = LoggerFactory.getLogger(DoConnection.class);
    public static void doConnection(Bootstrap bootstrap, EventLoopGroup eventExecutors) {
            ChannelFuture f = null;
            try {
                if (bootstrap != null) {
                    bootstrap.group(eventExecutors);
                    bootstrap.channel(NioSocketChannel.class);
                    bootstrap.option(ChannelOption.SO_KEEPALIVE, true)
                            .handler(new LoggingHandler(LogLevel.INFO))
                            .handler(new ClientInitializer()).
                            remoteAddress(TK.ktConfig.getAddress(), TK.ktConfig.getPort());
                    log.info("将进行重新连接");
                    bootstrap.connect().addListener((ChannelFuture futureListener) -> {
                        final EventLoop eventLoop = futureListener.channel().eventLoop();
                        if (futureListener.isSuccess()) {
                            log.info("连接成功");
                        } else {
                            log.info("与服务端断开连接!在3s之后准备尝试重连!(新建一个线程进行重连)");
                            eventLoop.schedule(new Runnable() {
                                @Override
                                public void run() {
                                    doConnection(new Bootstrap(), eventLoop);
                                }
                            },3,TimeUnit.SECONDS);
                        }
                    });
                }
            } catch (Exception e) {
                log.error("客户端连接失败，出现异常!",e);
            }
    }



}

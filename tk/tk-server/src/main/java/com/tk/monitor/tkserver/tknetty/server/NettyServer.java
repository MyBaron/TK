package com.tk.monitor.tkserver.tknetty.server;

import com.tk.monitor.tkserver.tknetty.client.CheckClientThread;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@ConfigurationProperties
public class NettyServer  implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(NettyServer.class);

    @Value("${tk.server.port}")
    private int port;

    public  void server() {
        EventLoopGroup boss = new NioEventLoopGroup(1);
        EventLoopGroup work = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, work)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ServerInitalizer());

            ChannelFuture future = bootstrap.bind(port).sync();
            // 监听服务器关闭监听
            log.info("netty服务器启动完成");
//            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            boss.shutdownGracefully();
            work.shutdownGracefully();
            log.info("netty服务器发生异常",e);
        }
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("开始启动netty服务器，端口："+port);
        //启动一个线程，检测断线的客户端
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new CheckClientThread());
        server();
    }
}

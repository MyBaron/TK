package com.tk.monitor.tkclient.message.tknetty.Client;

import com.tk.monitor.tkclient.message.Transaction;
import com.tk.monitor.tkclient.util.TK;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadFactory;

public class NettyClient {


    private static Logger log = LoggerFactory.getLogger(NettyClient.class);

    public static void client(int port,String address) {
        NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup(1, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setDaemon(true);
                return t;
            }
        });
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(nioEventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(address, port)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .handler(new ClientInitializer());
        ChannelFuture future = bootstrap.connect().addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    if(future.isSuccess()){
                        log.info("连接成功");
                    }else {
                        log.info("连接失败，开始重连");
                        DoConnection.doConnection(new Bootstrap(),nioEventLoopGroup);
                    }
                }
            });
        log.info("连接完成,将进行阻塞");
    }



    public static void main(String[] args) {
        TK.initialize();
        client(TK.ktConfig.getPort(),TK.ktConfig.getAddress());
       for (int i=0;i<10;i++) {
           Thread t = new Thread(new task("线程" + i));
           t.start();
       }
        try {
            Thread.sleep(100000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static class task implements Runnable {
        private String name;

        public task(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            String[] URLs = new String[8];
            URLs[0] = "order/index.html";
            URLs[1] = "user/login.do";
            URLs[2] = "order/createOrder.do";
            URLs[3] = "order/createAndPlay.do";
            URLs[4] = "order/play.do";
            URLs[5] = "order/checkOrder.do";
            URLs[6] = "good/checkGoodAll.do";
            URLs[7] = "good/createAndPlay.do";
            Random random = new Random();
            for (int i=0;i<100;i++) {
                int number = random.nextInt(5)+2;
                Transaction tran = TK.newTransaction("URL", URLs[5]);
                try {
                    int t = new Random().nextInt(500) + 400;
                    Thread.sleep(t);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                tran.complete("info",null);
            }
        }
    }
}



package com.monitoring.seckill.Util.ZookeeperUtil;

import com.monitoring.seckill.Util.PropertiesUtil;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CuratorZKUtil {


    private static final Logger logger = LoggerFactory.getLogger(CuratorZKUtil.class);
    private static CuratorFramework client;
    private static String ip;

    static {
        ip = PropertiesUtil.getProperty("zookeeper.ip", "10.10.10.10");
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(3000,3);
        client = CuratorFrameworkFactory.newClient(ip, retryPolicy);
        client.start();
    }

    public static CuratorFramework getClient() {
        return client;
    }

    public static InterProcessMutex distributedLock(String path) {
        return new InterProcessMutex(client, path);
    }


    public static void main(String[] args) {

    }

    public static void createExe() {
        Executor executor = Executors.newFixedThreadPool(15);
        executor.execute(new myThread("No1","/test"));
        executor.execute(new myThread("No2","/test"));
        executor.execute(new myThread("No3","/test"));
        executor.execute(new myThread("No4","/test"));
        executor.execute(new myThread("No5","/test"));
        executor.execute(new myThread("No6","/test"));
        executor.execute(new myThread("No7","/test"));
        executor.execute(new myThread("T1","/te"));
        executor.execute(new myThread("T2","/te"));
        executor.execute(new myThread("T3","/te"));
        executor.execute(new myThread("TT1","/te/te"));
        executor.execute(new myThread("TT2","/te/te"));
        executor.execute(new myThread("TT3","/te/te"));

    }

     static class myThread implements Runnable {

         private String name;
         private String path;

         public myThread(String name, String path) {
             this.name = name;
             this.path = path;
         }

         @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
             logger.info("{}尝试获取锁",name);
            InterProcessMutex lock=null;
            try {
                lock = distributedLock(path);
                lock.acquire();
                logger.info("{} : {} 获取锁， 睡眠2000ms",path,name);
                Thread.sleep(2000);
                logger.info("{} : {}  解除锁",path,name);
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if (null != lock) {
                    try {
                        lock.release();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }

}

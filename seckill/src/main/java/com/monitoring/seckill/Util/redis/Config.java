package com.monitoring.seckill.Util.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:redis.properties")
public abstract class Config {


    protected  static boolean turnOn;

    @Value("cluster.TurnOn")
    public static void setTurnOn(boolean turnOn) {
        Config.turnOn = turnOn;
    }

    //超时时间

    protected static int timeout;

    @Value("${timeout}")
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    //最大连接数
    protected  static int maxActive;

    @Value("${maxActive}")
    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    //最大空闲数
    protected  static int maxIdle;

    @Value("${maxIdle}")
    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    //连接最长空闲时间
    protected static int maxWait;

    @Value("${maxWait}")
    public void setMaxWait(int maxWait) {
        this.maxWait = maxWait;
    }

    //检测连接是否有效
    protected  static boolean testOnBorrow;

    @Value("${testOnBorrow}")
    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    //检测连接是否有效
    protected static String password;

    @Value("${password}")
    public void setPassword(String password) {
        this.password = password;
    }

    //连接超时时间
    protected static int soTimeout;

    @Value("${soTimeout}")
    public void setSoTimeout(int soTimeout) {
        this.soTimeout = soTimeout;
    }

    //尝试次数
    protected static int maxAttempts;

    @Value("${maxAttempts}")
    public void setMaxAttempts(int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }
}

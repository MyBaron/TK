package com.monitoring.seckill.Util.redis;


import redis.clients.jedis.JedisCommands;

import java.io.Closeable;

public class RedisUtil extends Config{

    private static volatile JedisClusterFactory jedisClusterFactory = null;
    private static volatile JedisClientFactory jedisClientFactory = null;


    /**
     * redis 集群
     */
    public static JedisClusterFactory getJedisClusterFactory() {
        if (null == jedisClusterFactory) {
            synchronized (RedisUtil.class) {
                if (null == jedisClusterFactory) {
                    jedisClusterFactory = new JedisClusterFactory();
                }
            }
        }
        return jedisClusterFactory;
    }


    /**
     * 获取单机连接
     */
    public static JedisClientFactory getJedisClientFactory(){
        if (null == jedisClientFactory) {
            synchronized (RedisUtil.class) {
                if (null == jedisClientFactory) {
                    jedisClientFactory = new JedisClientFactory();
                }
            }
        }
        return jedisClientFactory;
    }



    public static void close(JedisCommands jc) {
        if (!turnOn) {
            JedisClientFactory.close((Closeable)jc);
        }
    }
}

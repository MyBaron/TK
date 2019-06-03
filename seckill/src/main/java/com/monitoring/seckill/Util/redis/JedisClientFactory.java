package com.monitoring.seckill.Util.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import redis.clients.jedis.*;

import java.io.Closeable;
import java.io.IOException;

public class JedisClientFactory extends Config {


    //获取ip地址和端口号
    @Value("${redis.host}")
    private String host;

    //获取ip地址和端口号
    @Value("${redis.prot}")
    private int prot;


    private  JedisPool jedisPool;


    public static void close(Closeable jedis) {
        try {
            if (jedis != null)
                jedis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JedisPool createJedisCluster() {
        if (null == jedisPool) {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(maxActive);
            config.setMaxIdle(maxIdle);
            config.setMaxWaitMillis(maxWait);
            config.setTestOnBorrow(testOnBorrow);
            //创建连接
            jedisPool = new JedisPool(config, host, prot, timeout, password);
            Jedis resource = jedisPool.getResource();
        }
        return jedisPool;
    }

}

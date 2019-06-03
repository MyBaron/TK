package com.monitoring.seckill.Util.redis;


import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Component
@PropertySource("classpath:redis.properties")
public class JedisClusterFactory extends Config {


    //获取ip地址和端口号
    @Value("#{'${cluster.address}'.split(',')}")
    private Set<String> addressSet;


    // 集群连接工厂
    private JedisCluster jedisCluster = null;


    private  JedisCluster createJedisCluster() {
        Set<HostAndPort> jedisClusterNodes = new HashSet<>();
        addressSet.forEach(s -> jedisClusterNodes.add(HostAndPort.parseString(s)));
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxActive);
        config.setMaxIdle(maxIdle);
        config.setMaxWaitMillis(maxWait);
        config.setTestOnBorrow(testOnBorrow);
        //创建连接
        if (Strings.isNullOrEmpty(password)) {
            jedisCluster = new JedisCluster(jedisClusterNodes, timeout, soTimeout, maxAttempts, config);
        } else {
            jedisCluster = new JedisCluster(jedisClusterNodes, timeout, soTimeout, maxAttempts, password, config);
        }
        return jedisCluster;
    }



}

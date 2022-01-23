package com.imooc.miaosha.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

/**
 * redis连接池配置
 *
 * @author Justyn
 * @version 1.0
 * @date 2022/1/12 22:14
 */
@Configuration
public class RedisPoolFactory {

    @Autowired
    RedisConfig redisConfig;

    /**
     * redis连接池交给spring管理
     */
    @Bean
    JedisPool JedisPoolFactory() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(redisConfig.getPoolMaxTotal());
        config.setMaxIdle(redisConfig.getPoolMaxIdle());
        config.setMaxWait(Duration.ofSeconds(redisConfig.getPoolMaxWait()));
        JedisPool pool = new JedisPool(config, redisConfig.getHost(), redisConfig.getPort(), redisConfig.getTimeout(),
                redisConfig.getPassword(), redisConfig.getDatabase());
        return pool;
    }
}

package com.imooc.miaosha.jedis;

import lombok.Cleanup;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * TODO
 *
 * @author Justyn
 * @version 1.0
 * @date 2021/12/30 0:02
 */
@SpringBootTest
public class JedisTest {

    @Value("${redis.host}")
    private String host;
    @Value("${redis.port}")
    private int port;
    @Value("${redis.password}")
    private String password;

    @Test
    public void test() {
        Jedis jedis = new Jedis(host, port);
        jedis.auth(password);
        jedis.set("name", "张三");
        String name = jedis.get("namea");
        System.out.println(name);
        jedis.close();
    }

    /**
     * 连接池方式
     */
    @Test
    public void test2() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(30);
        config.setMaxIdle(10);
        @Cleanup JedisPool pool = new JedisPool(config, host);
        @Cleanup Jedis jedis = pool.getResource();
        jedis.auth(password);
        String str = jedis.set("sex", "男");
        System.out.println(str);
        System.out.println(jedis.get("sex"));
    }
}

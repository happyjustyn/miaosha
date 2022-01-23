package com.imooc.miaosha.redis;

import com.imooc.miaosha.domain.MiaoshaUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RedisServiceTest {

    @Autowired
    RedisService redisService;

    @Test
    void get() {
        MiaoshaUser user = redisService.get(MiaoshaUserKey.token, "str", MiaoshaUser.class);
        System.out.println(user);
    }

    @Test
    void set() {
        redisService.set(UserKey.getById, "str", "tom");
    }
}
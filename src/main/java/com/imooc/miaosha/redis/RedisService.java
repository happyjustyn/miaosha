package com.imooc.miaosha.redis;

import cn.hutool.json.JSONUtil;
import lombok.Cleanup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * redis业务类
 *
 * @author Justyn
 * @version 1.0
 * @date 2021/12/31 8:26
 */
@Service
public class RedisService {

    @Autowired
    JedisPool jedisPool;
    @Autowired
    RedisConfig redisConfig;

    /**
     * 获取数据
     */
    public <T> T get(KeyPrefix prefix, String key, Class<T> clazz) {
        @Cleanup Jedis jedis = jedisPool.getResource();
        String realKey = prefix.getPrefix() + key;
        String str = jedis.get(realKey);
        return strToBean(str, clazz);
    }

    /**
     * 存入数据
     */
    public <T> void set(KeyPrefix prefix, String key, T value) {
        @Cleanup Jedis jedis = jedisPool.getResource();
        String str;
        if (isPrimitive(value.getClass())) {
            str = String.valueOf(value);
        } else {
            str = JSONUtil.toJsonStr(value);
        }
        String realKey = prefix.getPrefix() + key;
        if (prefix.expireSecond() <= 0) {
            jedis.set(realKey, str);
        } else {
            jedis.setex(realKey, prefix.expireSecond(), str);
        }
    }

    public Long incr(KeyPrefix prefix, String key) {
        @Cleanup Jedis jedis = jedisPool.getResource();
        String realKey = prefix.getPrefix() + key;
        return jedis.incr(realKey);
    }

    public Long decr(KeyPrefix prefix, String key) {
        @Cleanup Jedis jedis = jedisPool.getResource();
        String realKey = prefix.getPrefix() + key;
        return jedis.decr(realKey);
    }

    public boolean delete(KeyPrefix prefix, String key) {
        @Cleanup Jedis jedis = jedisPool.getResource();
        String realKey = prefix.getPrefix() + key;
        Long num = jedis.del(realKey);
        return num > 0;
    }

    /**
     * 判断key存在
     */
    public boolean exsists(KeyPrefix prefix, String key) {
        @Cleanup Jedis jedis = jedisPool.getResource();
        String realKey = prefix.getPrefix() + key;
        return jedis.exists(realKey);
    }

    /**
     * 判断clazz是否是基本数据类型及其包装类
     */
    private boolean isPrimitive(Class<?> clazz) {
        // 判断基本类型
        if (clazz.isPrimitive()) {
            return true;
        }
        // 判断包装类
        try {
            return ((Class<?>) clazz.getField("TYPE").get(null)).isPrimitive();
        } catch (IllegalAccessException | NoSuchFieldException e) {
            return false;
        }
    }

    /**
     * 字符串转换为java数据类型
     */
    private <T> T strToBean(String str, Class<T> clazz) {
        if (null == str || str.length() <= 0 || null == clazz) {
            return null;
        }
        if (clazz == int.class || clazz == Integer.class) {
            return (T) Integer.valueOf(str);
        } else if (clazz == double.class || clazz == Double.class) {
            return (T) Double.valueOf(str);
        } else if (clazz == String.class) {
            return (T) str;
        } else {
            return JSONUtil.toBean(str, clazz);
        }
    }

}

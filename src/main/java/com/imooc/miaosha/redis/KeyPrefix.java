package com.imooc.miaosha.redis;

/**
 * key前缀封装类（为了避免不同模块redis的键可能重复的问题）
 *
 * @author Justyn
 * @version 1.0
 * @date 2021/12/31 15:10
 */
public interface KeyPrefix {

    /**
     * 过期时间
     */
    public long expireSecond();

    /**
     * 获取key前缀
     */
    public String getPrefix();
}

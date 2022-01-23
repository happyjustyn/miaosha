package com.imooc.miaosha.redis;

/**
 * key前缀封装实现
 *
 * @author Justyn
 * @version 1.0
 * @date 2021/12/31 15:13
 */
public abstract class BasePrefix implements KeyPrefix {

    /**
     * 过期时间（<=0代表永不过期）
     */
    private long expireSeconds;
    /**
     * key前缀
     */
    private String prefix;

    public BasePrefix(String prefix) {
        this(0, prefix);
    }

    public BasePrefix(long expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    @Override
    public long expireSecond() {
        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        String className = getClass().getSimpleName();
        return className + ":" + prefix;
    }
}

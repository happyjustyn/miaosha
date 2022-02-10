package com.imooc.miaosha.redis;

/**
 * @author Justyn
 * @version 1.0
 * @date 2022/1/12 19:30
 */
public class AccessKey extends BasePrefix {

    public static AccessKey withExpire(int expireSeconds) {
        return new AccessKey(expireSeconds, "access");
    }

    public AccessKey(String prefix) {
        super(prefix);
    }

    public AccessKey(long expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

}

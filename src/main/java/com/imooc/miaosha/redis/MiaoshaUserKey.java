package com.imooc.miaosha.redis;

/**
 * 秒杀用户key前缀
 *
 * @author Justyn
 * @version 1.0
 * @date 2021/12/31 15:20
 */
public class MiaoshaUserKey extends BasePrefix {

    /**
     * token过期时间
     */
    public static final long TOKEN_EXPIRE = 3600 * 24 * 2;

    public static MiaoshaUserKey token = new MiaoshaUserKey(TOKEN_EXPIRE, "tk");
    /**
     * 对象缓存通常不设置过期时间
     */
    public static MiaoshaUserKey getById = new MiaoshaUserKey(0, "id");


    private MiaoshaUserKey(long expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
}

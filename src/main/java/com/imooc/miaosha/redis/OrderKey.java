package com.imooc.miaosha.redis;

/**
 * 订单模块key前缀
 *
 * @author Justyn
 * @version 1.0
 * @date 2021/12/31 15:21
 */
public class OrderKey extends BasePrefix {

    public static OrderKey getMiaoshaOrderByUidGid = new OrderKey("moug");

    public OrderKey(String prefix) {
        super(prefix);
    }

    public OrderKey(long expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
}

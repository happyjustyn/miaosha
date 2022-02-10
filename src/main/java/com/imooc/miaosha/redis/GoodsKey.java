package com.imooc.miaosha.redis;

/**
 * 商品模块key前缀
 *
 * @author Justyn
 * @version 1.0
 * @date 2022/1/12 19:30
 */
public class GoodsKey extends BasePrefix {

    /**
     * 页面缓存有效期较短
     */
    public static GoodsKey getGoodsList = new GoodsKey(60, "gl");
    public static GoodsKey getGoodsDetail = new GoodsKey(60, "gd");
    public static GoodsKey getMiaoshaGoodsStock = new GoodsKey(0, "gs");


    public GoodsKey(String prefix) {
        super(prefix);
    }

    public GoodsKey(long expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

}

package com.imooc.miaosha.redis;

/**
 * 秒杀模块key前缀
 *
 * @author Justyn
 * @version 1.0
 * @date 2022/1/12 19:30
 */
public class MiaoshaKey extends BasePrefix {

    public static MiaoshaKey isGoodsOver = new MiaoshaKey("go");
    public static MiaoshaKey getMiaoshaPath = new MiaoshaKey(60, "mp");
    public static MiaoshaKey getMiaoshaVerifyCode = new MiaoshaKey(300, "vc");



    public MiaoshaKey(String prefix) {
        super(prefix);
    }

    public MiaoshaKey(long expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

}

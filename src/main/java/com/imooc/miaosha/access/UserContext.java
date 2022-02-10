package com.imooc.miaosha.access;

import com.imooc.miaosha.domain.MiaoshaUser;

/**
 * @author Justyn
 * @version 1.0
 * @date 2022/2/10 16:07
 */
public class UserContext {

    private static ThreadLocal<MiaoshaUser> userHolder = new ThreadLocal<>();

    public static void setUser(MiaoshaUser user) {
        userHolder.set(user);
    }

    public static MiaoshaUser getUser() {
        return userHolder.get();
    }

    public static void close() {
        userHolder.remove();
    }
}

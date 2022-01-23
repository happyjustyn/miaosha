package com.imooc.miaosha.redis;

/**
 * 用户模块key前缀
 *
 * @author Justyn
 * @version 1.0
 * @date 2021/12/31 15:20
 */
public class UserKey extends BasePrefix{

    public static UserKey getById = new UserKey("id");
    public static UserKey getByName = new UserKey("name");

    private UserKey(String prefix) {
        super(prefix);
    }
}

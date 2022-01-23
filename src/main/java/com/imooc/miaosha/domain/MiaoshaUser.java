package com.imooc.miaosha.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * miaosha_user
 */
@Data
public class MiaoshaUser implements Serializable {
    /**
     * 用户id, 手机号码
     */
    private Long id;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * MD5(MD5(password+salt)+salt)
     */
    private String password;

    /**
     * 盐
     */
    private String salt;

    /**
     * 头像, 云存储id
     */
    private String head;

    /**
     * 注册时间
     */
    private Date registerDate;

    /**
     * 上次登录时间
     */
    private Date lastLoginDate;

    /**
     * 总登录次数
     */
    private Integer loginCount;

    private static final long serialVersionUID = 1L;
}
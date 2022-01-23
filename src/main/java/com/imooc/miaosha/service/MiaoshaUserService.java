package com.imooc.miaosha.service;

import cn.hutool.core.util.IdUtil;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.dao.MiaoshaUserDao;
import com.imooc.miaosha.redis.MiaoshaUserKey;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.util.ExceptionUtil;
import com.imooc.miaosha.util.MD5Util;
import com.imooc.miaosha.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * TODO
 *
 * @author Justyn
 * @version 1.0
 * @date 2022/1/1 23:53
 */
@Service
public class MiaoshaUserService {

    public static final String COOKIE_NAME_TOKEN = "token";

    @Autowired
    MiaoshaUserDao miaoshaUserDao;
    @Autowired
    RedisService redisService;

    /**
     * 对象缓存实现过程
     */
    public MiaoshaUser getById(Long id) {
        // 取缓存
        MiaoshaUser user = redisService.get(MiaoshaUserKey.getById, "" + id, MiaoshaUser.class);
        if (user != null) {
            return user;
        }
        // 取数据库
        user = miaoshaUserDao.selectByPrimaryKey(id);
        if (user != null) {
            // 写入缓存
            redisService.set(MiaoshaUserKey.getById, "" + id, user);
        }
        return user;
    }

    /**
     * 对象缓存更新时候需要注意的操作
     * 注意先更新数据库，再更新缓存，反过来可能会造成数据不一致的情况
     */
    public boolean updatePassword(String token, Long id, String formPass) {
        MiaoshaUser user = getById(id);
        if (user == null) {
            throw ExceptionUtil.getGlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        // 更新数据库
        MiaoshaUser toBeUpdate = new MiaoshaUser();
        toBeUpdate.setId(user.getId());
        toBeUpdate.setPassword(MD5Util.formPassToDBPass(formPass, user.getSalt()));
        miaoshaUserDao.updateByPrimaryKeySelective(toBeUpdate);
        // 处理缓存
        redisService.delete(MiaoshaUserKey.getById, "" + id);
        user.setPassword(formPass);
        // 这里不能delete，否则用户无法登录
        redisService.set(MiaoshaUserKey.token, token, user);

        return true;
    }

    public String login(HttpServletResponse response, LoginVo loginVo) {
        String formPass = loginVo.getPassword();
        String mobile = loginVo.getMobile();
        // 判断用户存在
        MiaoshaUser user = miaoshaUserDao.selectByPrimaryKey(Long.parseLong(mobile));
        if (null == user) {
            throw ExceptionUtil.getGlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        // 验证密码
        String dbPass = user.getPassword();
        String salt = user.getSalt();
        String realPass = MD5Util.formPassToDBPass(formPass, salt);
        if (!dbPass.equals(realPass)) {
            throw ExceptionUtil.getGlobalException(CodeMsg.PASSWORD_ERROR);
        }
        // 登录成功生成token
        String token = IdUtil.simpleUUID();
        addCookie(response, token, user);
        return token;
    }

    public MiaoshaUser getByToken(HttpServletResponse response, String token) {
        MiaoshaUser user = redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);
        // 延长token有效期
        if (user != null) {
            addCookie(response, token, user);
        }
        return user;
    }

    /**
     * 分布式session实现：用户token存放到Redis，每次认证都从Redis中取
     */
    private void addCookie(HttpServletResponse response, String token, MiaoshaUser user) {
        // 将用户信息存到redis
        redisService.set(MiaoshaUserKey.token, token, user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge((int) MiaoshaUserKey.token.expireSecond());
        cookie.setPath("/");
        response.addCookie(cookie);
    }

}

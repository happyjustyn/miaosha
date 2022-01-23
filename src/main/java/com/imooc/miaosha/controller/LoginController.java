package com.imooc.miaosha.controller;

import com.imooc.miaosha.result.Result;
import com.imooc.miaosha.service.MiaoshaUserService;
import com.imooc.miaosha.vo.LoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 登录控制器
 *
 * @author Justyn
 * @version 1.0
 * @date 2022/1/1 19:47
 */
@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {

    @Autowired
    MiaoshaUserService miaoshaUserService;

    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }

    /**
     * 用户：11111111111 密码：123456
     */
    @PostMapping("/do_login")
    @ResponseBody
    public Result<String> doLogin(HttpServletResponse response, @Valid LoginVo loginVo) {
        // 登录（为了测试方便才返回token）
        String token = miaoshaUserService.login(response, loginVo);
        return Result.success(token);
    }

}

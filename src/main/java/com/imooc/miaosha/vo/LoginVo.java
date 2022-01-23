package com.imooc.miaosha.vo;

import com.imooc.miaosha.util.ValidatorUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 接收登录传参
 *
 * @author Justyn
 * @version 1.0
 * @date 2022/1/1 21:13
 */
@Getter
@Setter
@ToString
public class LoginVo {

    @NotNull(message = "手机号码不能为空")
    @Pattern(regexp = ValidatorUtil.MOBILE_PATTERN, message = "手机号码格式不正确")
    private String mobile;

    @NotNull(message = "密码不能为空")
    @Size(min = 32, message = "密码长度不能小于32位")
    private String password;
}

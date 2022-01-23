package com.imooc.miaosha.util;

import cn.hutool.core.util.ReUtil;

/**
 * 参数校验工具类
 *
 * @author Justyn
 * @version 1.0
 * @date 2022/1/1 22:45
 */
public class ValidatorUtil {

    /**
     * 手机号码正则
     */
    public static final String MOBILE_PATTERN = "1\\d{10}";

    public static boolean isMobile(String str) {
        return ReUtil.isMatch(MOBILE_PATTERN, str);
    }

}

package com.imooc.miaosha.util;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.DigestUtil;

/**
 * MD5工具类
 *
 * @author Justyn
 * @version 1.0
 * @date 2022/1/1 19:12
 */
public class MD5Util {

    /**
     * 前台md5用的盐
     */
    private static final String SALT = "1a2b3c4d";

    public static String md5(String src) {
        return SecureUtil.md5(src);
    }

    /**
     * 第一层md5:
     * 用户输入的pass转换为表单的pass，以提交到后台
     */
    public static String inputPassToFormPass(String inputPass) {
        // 加盐
        String str = "" + SALT.charAt(0) + SALT.charAt(2) + inputPass + SALT.charAt(5) + SALT.charAt(4);
        return md5(str);
    }

    /**
     * 第二层md5:
     * 前台传过来的pass转化为数据库存储的pass
     */
    public static String formPassToDBPass(String formPass, String salt) {
        // 加盐
        String str = "" + salt.charAt(0) + salt.charAt(2) + formPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    /**
     * 用户输入pass转化成数据库存储pass
     */
    public static String inputPassToDBPass(String inputPass, String salt) {
        String str = inputPassToFormPass(inputPass);
        return formPassToDBPass(str, salt);
    }


    public static void main(String[] args) {
        System.out.println(inputPassToDBPass("123456", SALT));
    }
}

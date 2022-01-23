package com.imooc.miaosha.util;

import com.imooc.miaosha.exception.GlobalException;
import com.imooc.miaosha.result.CodeMsg;

/**
 * 生成业务异常
 *
 * @author Justyn
 * @version 1.0
 * @date 2022/1/2 20:54
 */
public class ExceptionUtil {
    public static GlobalException getGlobalException(CodeMsg cm) {
        return new GlobalException(cm);
    }
}

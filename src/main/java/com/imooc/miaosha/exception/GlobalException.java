package com.imooc.miaosha.exception;

import com.imooc.miaosha.result.CodeMsg;
import lombok.Getter;

/**
 * 全局通用业务异常
 *
 * @author Justyn
 * @version 1.0
 * @date 2022/1/2 20:39
 */
@Getter
public class GlobalException extends RuntimeException {

    private CodeMsg cm;

    public GlobalException(CodeMsg cm) {
        super(cm.getMsg());
        this.cm = cm;
    }

}

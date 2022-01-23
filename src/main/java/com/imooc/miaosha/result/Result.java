package com.imooc.miaosha.result;

import cn.hutool.core.util.ObjectUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * 后端接口返回结果
 *
 * @author Justyn
 * @version 1.0
 * @date 2021/12/29 18:47
 */
@Setter
@Getter
public class Result<T> {
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 信息
     */
    private String msg;
    /**
     * 返回数据
     */
    private T data;

    private Result(T data) {
        this.code = 0;
        this.msg = "success";
        this.data = data;
    }

    private Result(CodeMsg cm) {
        this.code = cm.getCode();
        this.msg = cm.getMsg();
    }

    public static <T> Result<T> success(T data) {
        return new Result<T>(data);
    }

    public static <T> Result<T> error(CodeMsg cm) {
        return new Result<>(cm);
    }

    public static <T> Result<T> error(CodeMsg cm, String extraMsg) {
        Result<T> result = error(cm);
        result.setMsg(result.getMsg() + ":" + extraMsg);
        return result;
    }
}


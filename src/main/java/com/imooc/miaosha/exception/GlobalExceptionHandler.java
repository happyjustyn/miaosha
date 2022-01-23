package com.imooc.miaosha.exception;

import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * 全局异常处理器
 *
 * @author Justyn
 * @version 1.0
 * @date 2022/1/2 19:04
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * @Validated @Valid 的校验异常通常有三种：
     * BindException,MethodArgumentNotValidException,ConstraintViolationException
     * 需在异常处理器中处理
     */
    @ExceptionHandler(BindException.class)
    public Result<String> bindExceptionHandler(BindException e) {
        List<FieldError> list = e.getBindingResult().getFieldErrors();
        StringBuilder sb = new StringBuilder();
        list.forEach(fieldError -> sb.append(fieldError.getDefaultMessage()).append("-"));
        log.error(e.getMessage(), e);
        return Result.error(CodeMsg.ARGS_ERROR, sb.toString());
    }

    /**
     * 拦截业务异常
     */
    @ExceptionHandler(GlobalException.class)
    public Result<String> globalExceptionHandler(GlobalException e) {
        log.error(e.getMessage(), e);
        return Result.error(e.getCm());
    }

    @ExceptionHandler(Exception.class)
    public Result<String> exceptionHandler(Exception e) {
        log.error(e.getMessage(), e);
        return Result.error(CodeMsg.SERVER_ERROR);
    }
}

package com.woo.handler.exception;

import com.woo.domain.ResponseResult;
import com.woo.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(SystemException.class)
    public ResponseResult systemExceptionHandler(SystemException e){
        //打印异常信息
        log.error("出现了异常 {}",e.toString());
        //从异常对象中获取提示信息封装，然后返回
        return ResponseResult.errorResult(e.getCode(), e.getMsg());
    }
}

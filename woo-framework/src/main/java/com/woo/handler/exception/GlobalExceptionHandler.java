package com.woo.handler.exception;

import com.woo.domain.ResponseResult;
import com.woo.enums.AppHttpCodeEnum;
import com.woo.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

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
    //处理springsecurity的权限异常
//    @ExceptionHandler(AccessDeniedException.class)
//    public ResponseResult handleAccessDeniedException(AccessDeniedException e){
//        return ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH.getCode(),e.getMessage());
//    }
//    //将其他的异常交给这里进行处理
//    @ExceptionHandler(Exception.class)
//    public ResponseResult exceptionHandler(Exception e) {
//        log.error("出现异常信息"+e);
//        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),e.getMessage());
//    }
}

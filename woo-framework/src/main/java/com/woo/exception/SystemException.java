package com.woo.exception;

import com.woo.enums.AppHttpCodeEnum;

public class SystemException extends RuntimeException{
    private int code;
    private String msg;
    public int getCode(){
        return code;
    }
    public String getMsg(){
        return msg;
    }
    public SystemException(AppHttpCodeEnum httpCodeEnum){
        this.code = httpCodeEnum.getCode();
        this.msg = httpCodeEnum.getMsg();
    }
}

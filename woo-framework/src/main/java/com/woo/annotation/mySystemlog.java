package com.woo.annotation;

import org.aspectj.lang.annotation.Around;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface mySystemlog {
    /**为Controler提供接口的描述信息， 用于日志记录功能
     * @return {@link String}
     */
    String businessName();
}

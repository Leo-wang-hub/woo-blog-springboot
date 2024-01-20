package com.woo.aspect;

import com.alibaba.fastjson.JSON;
import com.woo.annotation.mySystemlog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;

@Component
@Aspect
@Slf4j
public class myLogAspect {
    @Pointcut("@annotation(com.woo.annotation.mySystemlog)")
    public void pt() {

    }
    @Around("pt()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
        //方法执行之后的返回值
        Object ret = null;
        try {
            handleBefore(joinPoint);
            ret = joinPoint.proceed();
            handleAfter(ret);
        } finally {
            log.info("=======================end=======================" + System.lineSeparator());
        }
        return ret;
    }

    /**方法调用之前调用
     * @param joinPoint
     */
    private void handleBefore(ProceedingJoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        mySystemlog systemlog = getSystemlog(joinPoint);
        log.info("======================Start======================");
        // 打印请求 URL
        log.info("请求URL   : {}", request.getRequestURL());
        // 打印描述信息，例如获取UserController类的updateUserInfo方法上一行的@mySystemlog注解的描述信息
        log.info("接口描述   : {}", systemlog.businessName());
        // 打印 Http method
        log.info("请求方式   : {}", request.getMethod());
        // 打印调用 controller 的全路径(全类名)、方法名
        log.info("请求类名   : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        // 打印请求的 IP
        log.info("访问IP    : {}", request.getRemoteHost());
        // 打印请求入参。JSON.toJSONString十FastJson提供的工具方法，能把数组转成JSON
        log.info("传入参数   : {}", JSON.toJSONString(joinPoint.getArgs()));
    }

    private mySystemlog getSystemlog(ProceedingJoinPoint joinPoint) {
         MethodSignature methodSignature= (MethodSignature) joinPoint.getSignature();
         mySystemlog annotation = methodSignature.getMethod().getAnnotation(mySystemlog.class);
         return annotation;
    }


    /**方法调用之后执行
     * @param ret
     */
    private void handleAfter(Object ret) {
        //打印参数
        log.info("返回参数:{}", JSON.toJSONString(ret));

    }

}

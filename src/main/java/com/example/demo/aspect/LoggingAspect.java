package com.example.demo.aspect;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Autowired
    private LoggingFilter loggingFilter;

    @Autowired
    private HttpServletRequest httpServletRequest;

    // 定義切入點：攔截所有控制器的方法
    @Pointcut("within(com.example.demo.controller..*)")
    public void controllerMethods() {}

    // 在方法執行之前
    @Before("controllerMethods()")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Aspect: Entering method {}.{}() with arguments: {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                joinPoint.getArgs());

        // 調用 LoggingFilter 的邏輯
        if (httpServletRequest instanceof HttpServletRequest) {
            loggingFilter.logRequest(httpServletRequest);
        }
    }
}

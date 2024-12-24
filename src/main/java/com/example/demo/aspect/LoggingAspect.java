package com.example.demo.aspect;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.example.demo.service.DemoService.*(..))")
    public void logBeforeMethod() {
        System.out.println("A method in DemoService is about to be executed.");
    }
}

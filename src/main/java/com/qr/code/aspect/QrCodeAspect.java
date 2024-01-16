package com.qr.code.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class QrCodeAspect {

    @Before("execution(* com.qr.code.controller.*.*(..))")
    public void logBeforeMethod(JoinPoint joinPoint) {
        System.out.println("Before executing method: " + joinPoint.getSignature().toShortString());
    }

    @AfterReturning(pointcut = "execution(* com.qr.code.controller.*.*(..))", returning = "result")
    public void logAfterReturningMethod(JoinPoint joinPoint, Object result) {
        System.out.println("After executing method: " + joinPoint.getSignature().toShortString());
        System.out.println("Result: " + result);
    }

    @AfterThrowing(pointcut = "execution(* com.qr.code.*.*.*(..))", throwing = "exception")
    public void logAfterThrowingMethod(JoinPoint joinPoint, Throwable exception) {
        System.out.println("Exception after executing method: " + joinPoint.getSignature().toShortString());
        System.out.println("Exception: " + exception.getMessage());
    }
}

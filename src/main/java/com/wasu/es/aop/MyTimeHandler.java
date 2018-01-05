package com.wasu.es.aop;

import lombok.extern.log4j.Log4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by MASTER_L on 2017/12/11.
 */
@Component
@Aspect
@Log4j
public class MyTimeHandler {

    @Pointcut("execution(* com.wasu.es.service.impl.*.*(..))")
    private void anyMethod() {
    }//定义一个切入点

    @Around("anyMethod()")
    public Object around(ProceedingJoinPoint pjp) {
        Object object = null;
        try {
            long start = System.currentTimeMillis();
            object = pjp.proceed();
            long end = System.currentTimeMillis();
            System.out.println(pjp.getSignature().getDeclaringType().getName() + "." + pjp.getSignature().getName() + "---耗时=" + (end - start));
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return object;
    }
}

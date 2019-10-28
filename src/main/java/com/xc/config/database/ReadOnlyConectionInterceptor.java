package com.xc.config.database;

import com.xc.config.db.DataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)
@Slf4j
public class ReadOnlyConectionInterceptor implements Ordered {

    @Around("@annotation(readOnlyConection)")
    public Object proceed(ProceedingJoinPoint proceedingJoinPoint, ReadOnlyConection readOnlyConection) throws Throwable {
        try {
            log.info("-----------------set database connection 2 read only ----------------------------");
            DataSourceContextHolder.read();
            Object result = proceedingJoinPoint.proceed();
            return result;
        } finally {
            DataSourceContextHolder.clear();
            log.info("-----------------restore database connection ----------------------------");
        }

    }

    @Override
    public int getOrder() {
        return 0;
    }

}

package com.xc.config.aop;

import com.xc.config.db.DataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)
@Slf4j
public class DataSourceAspect {

    @Before("execution(* com.xc..*.*ServiceImpl.find*(..))" +
            "|| execution(* com.xc..*.*ServiceImpl.count*(..))" +
            "|| execution(* com.xc..*.*ServiceImpl.sel*(..))" +
            "|| execution(* com.xc..*.*ServiceImpl.get*(..))"
    )
    public void setReadDataSourceType() {
        log.debug("拦截[read]方法");
        DataSourceContextHolder.read();
    }


    @Before("execution(* com.xc..*.*ServiceImpl.insert*(..))" +
            "|| execution(* com.xc..*.*ServiceImpl.save*(..))" +
            "|| execution(* com.xc..*.*ServiceImpl.update*(..))" +
            "|| execution(* com.xc..*.*ServiceImpl.set*(..))" +
            "|| execution(* com.xc..*.*ServiceImpl.del*(..))"
    )
    public void setWriteDataSourceType() {
        log.debug("拦截[write]操作");
        DataSourceContextHolder.write();
    }

}

package com.xc.config.db;

import lombok.extern.slf4j.Slf4j;

/**
 * 数据源
 */
@Slf4j
public class DataSourceContextHolder {

    private final static ThreadLocal<String> local = new ThreadLocal();

    public static ThreadLocal<String> getLocal() {
        return local;
    }

    public static void read() {
        log.debug("切换至[读]数据源");
        local.set(DataSourceType.READ.getType());
    }

    public static void write() {
        log.debug("切换至[写]数据源");
        local.set(DataSourceType.WRITE.getType());
    }

    public static String getJdbcType() {
        return local.get() == null ? DataSourceType.WRITE.getType() : local.get();
    }

    public static void clear() {
        local.remove();
    }
}

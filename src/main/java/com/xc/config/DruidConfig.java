package com.xc.config;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * 数据源配置文件
 */
@Configuration
@Slf4j
public class DruidConfig {

    /**
     * 主数据源
     * @return
     */
    @Primary
    @Bean(name = "writeDataSource")
    @ConfigurationProperties(prefix = "spring.writeDataSource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().type(DruidDataSource.class).build();
    }

    /**
     * 从数据源1
     * @return
     */
    @Bean(name = "readDataSource0")
    @ConfigurationProperties(prefix = "spring.readDataSource.read0")
    public DataSource readDataSource0() {
        return DataSourceBuilder.create().type(DruidDataSource.class).build();
    }

    /**
     * 从数据源2
     * @return
     */
    @Bean(name = "readDataSource1")
    @ConfigurationProperties(prefix = "spring.readDataSource.read1")
    public DataSource readDataSource1() {
        return DataSourceBuilder.create().type(DruidDataSource.class).build();
    }

}

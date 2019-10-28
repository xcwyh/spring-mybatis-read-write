package com.xc.config;

import com.baomidou.mybatisplus.spring.boot.starter.SpringBootVFS;
import com.xc.config.db.DataSourceType;
import com.xc.config.db.RoutingDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import org.springframework.core.io.Resource;
import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据源配置
 */
@Configuration
@EnableTransactionManagement(order = 2)
@MapperScan(basePackages = {"com.xc.mapper"})
//@AutoConfigureAfter({DruidConfig.class})
public class MybatisConfig implements TransactionManagementConfigurer, ApplicationContextAware {

    private static ApplicationContext context;

    /**
     *  写库数据源
     */
    @Autowired
    private DataSource writeDataSource;

    /**
     * 读数据源数量
     */
    @Value("${spring.readDataSource.readsize}")
    private Integer readsize;

    @Bean
    public AbstractRoutingDataSource routingDataSouceProxy() {
        RoutingDataSource proxy = new RoutingDataSource(readsize);
        Map<Object, Object> targetDatSources = new HashMap(readsize + 1);
        targetDatSources.put(DataSourceType.WRITE.getType(), writeDataSource);
        for (int i = 0; i < readsize; i++) {
            DataSource d = context.getBean("readDataSource" + i, DataSource.class);
            targetDatSources.put(i, d);
        }
        proxy.setDefaultTargetDataSource(writeDataSource);
        proxy.setTargetDataSources(targetDatSources);
        return proxy;
    }

    @Bean
    @ConditionalOnMissingBean
    public SqlSessionFactoryBean sqlSessionFactory() throws IOException {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(routingDataSouceProxy());
        bean.setVfs(SpringBootVFS.class);
        bean.setTypeAliasesPackage("com.xc");
        Resource configResource = new ClassPathResource("/mybatis-config.xml");
        bean.setConfigLocation(configResource);
        ResourcePatternResolver mapperResource = new PathMatchingResourcePatternResolver();
        bean.setMapperLocations(mapperResource.getResources("classpath*:mapper/**/*.xml"));
        return bean;
    }


    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(routingDataSouceProxy());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (context == null) {
            context = applicationContext;
        }
    }
}

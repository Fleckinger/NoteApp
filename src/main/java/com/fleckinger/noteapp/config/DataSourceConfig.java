package com.fleckinger.noteapp.config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("WEB-INF/dbConfig.properties")
public class DataSourceConfig {
    @Value("${db.url}")
    private String dbUrl;
    @Value("${db.user}")
    private String dbUser;
    @Value("${db.password}")
    private String dbPassword;

    @Bean
    public DataSource getDataSource() {
        PoolProperties poolProperties = new PoolProperties();
        poolProperties.setUrl(dbUrl);
        poolProperties.setUsername(dbUser);
        poolProperties.setPassword(dbPassword);
        poolProperties.setDriverClassName("com.mysql.jdbc.Driver");
        poolProperties.setValidationQuery("SELECT 1");
        poolProperties.setTestOnBorrow(true);
        poolProperties.setTestOnReturn(true);
        poolProperties.setValidationInterval(30000);
        poolProperties.setTimeBetweenEvictionRunsMillis(30000);
        poolProperties.setMaxActive(10);
        poolProperties.setInitialSize(2);
        poolProperties.setRemoveAbandonedTimeout(60);
        poolProperties.setMinIdle(1);
        poolProperties.setRemoveAbandoned(true);
        poolProperties.setJdbcInterceptors("org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"+
                "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");

        DataSource dataSource = new DataSource();
        dataSource.setPoolProperties(poolProperties);

        return dataSource;
    }
}

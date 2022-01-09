package com.fleckinger.noteapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * The class represents database connect configuration.
 */

@Configuration
@PropertySource("WEB-INF/dbConfig.properties")
public class JdbcConfig {
    @Value("${db.url}")
    private String dbUrl;
    @Value("${db.user}")
    private String dbUser;
    @Value("${db.password}")
    private String dbPassword;

    /**
     * @return Connection to the database
     */

    @Bean
    @Scope("prototype")
    public Connection getConnection() {

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
}

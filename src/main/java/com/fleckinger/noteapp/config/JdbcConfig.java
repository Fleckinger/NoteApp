package com.fleckinger.noteapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * The class represents database connect configuration.
 */

@Configuration
public class JdbcConfig {

    /**
     * Read username, password and path from dbConfig.properties and return Connection to database
     * dbConfig.properties placed in WEB-INF folder in war
     * @return Connection to database
     */
    @Bean
    public Connection getConnection() throws IOException {
        Properties properties = new Properties();
        Connection connection = null;

        try (InputStream input = new FileInputStream(
                this.getClass().getResource("../../../../../dbConfig.properties").getPath())) {
            properties.load(input);
            connection = DriverManager.getConnection(
                    properties.getProperty("db.url"),
                    properties.getProperty("db.user"),
                    properties.getProperty("db.password"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}

package com.nicmora.springbatchdemo.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.support.JdbcTransactionManager;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class BatchDataSourceConfig {

    private final Environment env;

    @Bean(name = "batchDataSource")
    public DataSource batchDataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(env.getProperty("spring.datasource.batch.driverClassName"));
        config.setJdbcUrl(env.getProperty("spring.datasource.batch.url"));
        config.setUsername(env.getProperty("spring.datasource.batch.username"));
        config.setPassword(env.getProperty("spring.datasource.batch.password"));
        config.setSchema("batch");
        config.addDataSourceProperty("currentSchema", "batch");

        return new HikariDataSource(config);
    }

    @Bean("batchTransactionManager")
    public JdbcTransactionManager batchTransactionManager(@Qualifier("batchDataSource") DataSource dataSource) {
        return new JdbcTransactionManager(dataSource);
    }

}


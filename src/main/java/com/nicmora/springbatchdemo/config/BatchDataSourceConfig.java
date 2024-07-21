package com.nicmora.springbatchdemo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
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
        return DataSourceBuilder.create()
                .driverClassName(env.getProperty("spring.datasource.batch.driverClassName"))
                .url(env.getProperty("spring.datasource.batch.url"))
                .username(env.getProperty("spring.datasource.batch.username"))
                .password(env.getProperty("spring.datasource.batch.password"))
                .build();
    }

    @Bean("batchTransactionManager")
    public JdbcTransactionManager batchTransactionManager(@Qualifier("batchDataSource") DataSource dataSource) {
        return new JdbcTransactionManager(dataSource);
    }

}


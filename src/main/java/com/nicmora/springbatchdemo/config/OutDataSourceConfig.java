package com.nicmora.springbatchdemo.config;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.nicmora.springbatchdemo.repository.out",
        entityManagerFactoryRef = "outEntityManagerFactory",
        transactionManagerRef = "outTransactionManager"
)
public class OutDataSourceConfig {

    private final Environment env;

    @Primary
    @Bean(name = "outDataSource")
    public DataSource outDataSource() {
        return DataSourceBuilder.create()
                .driverClassName(env.getProperty("spring.datasource.out.driverClassName"))
                .url(env.getProperty("spring.datasource.out.url"))
                .username(env.getProperty("spring.datasource.out.username"))
                .password(env.getProperty("spring.datasource.out.password"))
                .build();
    }

    @Primary
    @Bean(name = "outEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean outEntityManagerFactory(@Qualifier("outDataSource") DataSource dataSource) {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(true);
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setJpaVendorAdapter(vendorAdapter);
        entityManagerFactory.setPackagesToScan("com.nicmora.springbatchdemo.entity.out");
        entityManagerFactory.setDataSource(dataSource);

        return entityManagerFactory;
    }

    @Primary
    @Bean(name = "outTransactionManager")
    public PlatformTransactionManager outTransactionManager(@Qualifier("outEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);

        return transactionManager;
    }
}

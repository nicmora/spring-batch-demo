package com.nicmora.springbatchdemo.config;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
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
        basePackages = "com.nicmora.springbatchdemo.repository.in",
        entityManagerFactoryRef = "inEntityManagerFactory",
        transactionManagerRef = "inTransactionManager"
)
public class InDataSourceConfig {

    private final Environment env;

    @Bean(name = "inDataSource")
    public DataSource inDataSource() {
        return DataSourceBuilder.create()
                .driverClassName(env.getProperty("spring.datasource.in.driverClassName"))
                .url(env.getProperty("spring.datasource.in.url"))
                .username(env.getProperty("spring.datasource.in.username"))
                .password(env.getProperty("spring.datasource.in.password"))
                .build();
    }

    @Bean(name = "inEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean inEntityManagerFactory(@Qualifier("inDataSource") DataSource dataSource) {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(true);

        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setJpaVendorAdapter(vendorAdapter);
        entityManagerFactory.setPackagesToScan("com.nicmora.springbatchdemo.entity.in");
        entityManagerFactory.setDataSource(dataSource);

//        Properties properties = new Properties();
//        properties.put("hibernate.default_schema", "country");
//        factory.setJpaProperties(properties);

        return entityManagerFactory;
    }

    @Bean(name = "inTransactionManager")
    public PlatformTransactionManager inTransactionManager(@Qualifier("inEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);

        return transactionManager;
    }
}

package com.coralstay.pathfinderspringbackend.baseline.persistence;

import javax.sql.DataSource;

import jakarta.persistence.EntityManagerFactory;

import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.coralstay.pathfinderspringbackend.baseline",
        entityManagerFactoryRef = "baselineEntityManagerFactory",
        transactionManagerRef = "baselineTransactionManager"
)
public class BaselinePersistenceConfig {

    @Primary
    @Bean
    @ConfigurationProperties("app.datasource.baseline")
    public DataSourceProperties baselineDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean
    public DataSource baselineDataSource() {
        return baselineDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean baselineEntityManagerFactory() {
        var factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(baselineDataSource());
        factory.setPackagesToScan("com.coralstay.pathfinderspringbackend.baseline");
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factory.setPersistenceUnitName("baseline");
        return factory;
    }

    @Primary
    @Bean
    public PlatformTransactionManager baselineTransactionManager(
            EntityManagerFactory baselineEntityManagerFactory) {
        return new JpaTransactionManager(baselineEntityManagerFactory);
    }
}

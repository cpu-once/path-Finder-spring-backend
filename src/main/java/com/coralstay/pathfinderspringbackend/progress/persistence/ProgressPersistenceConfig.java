package com.coralstay.pathfinderspringbackend.progress.persistence;

import javax.sql.DataSource;

import jakarta.persistence.EntityManagerFactory;

import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.coralstay.pathfinderspringbackend.progress",
        entityManagerFactoryRef = "progressEntityManagerFactory",
        transactionManagerRef = "progressTransactionManager"
)
public class ProgressPersistenceConfig {

    @Bean
    @ConfigurationProperties("app.datasource.progress")
    public DataSourceProperties progressDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource progressDataSource() {
        return progressDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean progressEntityManagerFactory() {
        var factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(progressDataSource());
        factory.setPackagesToScan("com.coralstay.pathfinderspringbackend.progress");
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factory.setPersistenceUnitName("progress");
        return factory;
    }

    @Bean
    public PlatformTransactionManager progressTransactionManager(
            EntityManagerFactory progressEntityManagerFactory) {
        return new JpaTransactionManager(progressEntityManagerFactory);
    }
}

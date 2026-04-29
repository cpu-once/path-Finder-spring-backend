package com.coralstay.pathfinderspringbackend.progress.infrastructure;

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
        basePackages = ProgressPersistenceConfig.BASE_PACKAGE,
        entityManagerFactoryRef = "progressEntityManagerFactory",
        transactionManagerRef = "progressTransactionManager"
)
public class ProgressPersistenceConfig {

    public static final String BASE_PACKAGE = "com.coralstay.pathfinderspringbackend.progress";
    public static final String DATASOURCE_PREFIX = "app.datasource.progress";
    public static final String UNIT_NAME = "progress";

    @Bean
    @ConfigurationProperties(DATASOURCE_PREFIX)
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
        factory.setPackagesToScan(BASE_PACKAGE);
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factory.setPersistenceUnitName(UNIT_NAME);
        return factory;
    }

    @Bean
    public PlatformTransactionManager progressTransactionManager(
            EntityManagerFactory progressEntityManagerFactory) {
        return new JpaTransactionManager(progressEntityManagerFactory);
    }
}

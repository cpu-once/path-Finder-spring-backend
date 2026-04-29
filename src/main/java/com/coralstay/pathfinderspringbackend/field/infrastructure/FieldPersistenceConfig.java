package com.coralstay.pathfinderspringbackend.field.infrastructure;

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
        basePackages = FieldPersistenceConfig.BASE_PACKAGE,
        entityManagerFactoryRef = "fieldEntityManagerFactory",
        transactionManagerRef = "fieldTransactionManager"
)
public class FieldPersistenceConfig {

    public static final String BASE_PACKAGE = "com.coralstay.pathfinderspringbackend.field";
    public static final String DATASOURCE_PREFIX = "app.datasource.field";
    public static final String UNIT_NAME = "field";

    @Bean
    @ConfigurationProperties(DATASOURCE_PREFIX)
    public DataSourceProperties fieldDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource fieldDataSource() {
        return fieldDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean fieldEntityManagerFactory() {
        var factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(fieldDataSource());
        factory.setPackagesToScan(BASE_PACKAGE);
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factory.setPersistenceUnitName(UNIT_NAME);
        return factory;
    }

    @Bean
    public PlatformTransactionManager fieldTransactionManager(
            EntityManagerFactory fieldEntityManagerFactory) {
        return new JpaTransactionManager(fieldEntityManagerFactory);
    }
}

package com.coralstay.pathfinderspringbackend.field.persistence;

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
        basePackages = "com.coralstay.pathfinderspringbackend.field",
        entityManagerFactoryRef = "fieldEntityManagerFactory",
        transactionManagerRef = "fieldTransactionManager"
)
public class FieldPersistenceConfig {

    @Bean
    @ConfigurationProperties("app.datasource.field")
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
        factory.setPackagesToScan("com.coralstay.pathfinderspringbackend.field");
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factory.setPersistenceUnitName("field");
        return factory;
    }

    @Bean
    public PlatformTransactionManager fieldTransactionManager(
            EntityManagerFactory fieldEntityManagerFactory) {
        return new JpaTransactionManager(fieldEntityManagerFactory);
    }
}

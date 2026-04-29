package com.coralstay.pathfinderspringbackend.field.infrastructure;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        basePackages = FieldPersistenceConstants.PACKAGE,
        entityManagerFactoryRef = FieldPersistenceConstants.ENTITY_MANAGER_FACTORY,
        transactionManagerRef = FieldPersistenceConstants.TRANSACTION_MANAGER
)
public class FieldPersistenceConfig {

    @Bean
    @ConfigurationProperties(FieldPersistenceConstants.DATASOURCE_PROPERTIES)
    public DataSourceProperties fieldDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource fieldDataSource() {
        return fieldDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean(name = FieldPersistenceConstants.ENTITY_MANAGER_FACTORY)
    public LocalContainerEntityManagerFactoryBean fieldEntityManagerFactory() {
        var factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(fieldDataSource());
        factory.setPackagesToScan(FieldPersistenceConstants.PACKAGE);
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factory.setPersistenceUnitName(FieldPersistenceConstants.PERSISTENCE_UNIT);
        return factory;
    }

    @Bean(name = FieldPersistenceConstants.TRANSACTION_MANAGER)
    public PlatformTransactionManager fieldTransactionManager(
            EntityManagerFactory fieldEntityManagerFactory) {
        return new JpaTransactionManager(fieldEntityManagerFactory);
    }
}

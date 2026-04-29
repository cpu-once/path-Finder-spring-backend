package com.coralstay.pathfinderspringbackend.progress.infrastructure;

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
        basePackages = ProgressPersistenceConstants.PACKAGE,
        entityManagerFactoryRef = ProgressPersistenceConstants.ENTITY_MANAGER_FACTORY,
        transactionManagerRef = ProgressPersistenceConstants.TRANSACTION_MANAGER
)
public class ProgressPersistenceConfig {

    @Bean
    @ConfigurationProperties(ProgressPersistenceConstants.DATASOURCE_PROPERTIES)
    public DataSourceProperties progressDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource progressDataSource() {
        return progressDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean(name = ProgressPersistenceConstants.ENTITY_MANAGER_FACTORY)
    public LocalContainerEntityManagerFactoryBean progressEntityManagerFactory() {
        var factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(progressDataSource());
        factory.setPackagesToScan(ProgressPersistenceConstants.PACKAGE);
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factory.setPersistenceUnitName(ProgressPersistenceConstants.PERSISTENCE_UNIT);
        return factory;
    }

    @Bean(name = ProgressPersistenceConstants.TRANSACTION_MANAGER)
    public PlatformTransactionManager progressTransactionManager(
            EntityManagerFactory progressEntityManagerFactory) {
        return new JpaTransactionManager(progressEntityManagerFactory);
    }
}

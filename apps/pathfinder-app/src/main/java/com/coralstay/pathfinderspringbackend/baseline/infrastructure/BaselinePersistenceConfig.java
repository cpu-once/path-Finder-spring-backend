package com.coralstay.pathfinderspringbackend.baseline.infrastructure;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        basePackages = BaselinePersistenceConstants.PACKAGE,
        entityManagerFactoryRef = BaselinePersistenceConstants.ENTITY_MANAGER_FACTORY,
        transactionManagerRef = BaselinePersistenceConstants.TRANSACTION_MANAGER
)
public class BaselinePersistenceConfig {

    @Primary
    @Bean
    @ConfigurationProperties(BaselinePersistenceConstants.DATASOURCE_PROPERTIES)
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
    @Bean(name = BaselinePersistenceConstants.ENTITY_MANAGER_FACTORY)
    public LocalContainerEntityManagerFactoryBean baselineEntityManagerFactory() {
        var factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(baselineDataSource());
        factory.setPackagesToScan(BaselinePersistenceConstants.PACKAGE);
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factory.setPersistenceUnitName(BaselinePersistenceConstants.PERSISTENCE_UNIT);
        return factory;
    }

    @Primary
    @Bean(name = BaselinePersistenceConstants.TRANSACTION_MANAGER)
    public PlatformTransactionManager baselineTransactionManager(
            EntityManagerFactory baselineEntityManagerFactory) {
        return new JpaTransactionManager(baselineEntityManagerFactory);
    }
}

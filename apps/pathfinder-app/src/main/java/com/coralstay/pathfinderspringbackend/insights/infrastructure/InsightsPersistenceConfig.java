package com.coralstay.pathfinderspringbackend.insights.infrastructure;

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
        basePackages = InsightsPersistenceConstants.PACKAGE,
        entityManagerFactoryRef = InsightsPersistenceConstants.ENTITY_MANAGER_FACTORY,
        transactionManagerRef = InsightsPersistenceConstants.TRANSACTION_MANAGER
)
public class InsightsPersistenceConfig {

    @Bean
    @ConfigurationProperties(InsightsPersistenceConstants.DATASOURCE_PROPERTIES)
    public DataSourceProperties insightsDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource insightsDataSource() {
        return insightsDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean(name = InsightsPersistenceConstants.ENTITY_MANAGER_FACTORY)
    public LocalContainerEntityManagerFactoryBean insightsEntityManagerFactory() {
        var factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(insightsDataSource());
        factory.setPackagesToScan(InsightsPersistenceConstants.PACKAGE);
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factory.setPersistenceUnitName(InsightsPersistenceConstants.PERSISTENCE_UNIT);
        return factory;
    }

    @Bean(name = InsightsPersistenceConstants.TRANSACTION_MANAGER)
    public PlatformTransactionManager insightsTransactionManager(
            EntityManagerFactory insightsEntityManagerFactory) {
        return new JpaTransactionManager(insightsEntityManagerFactory);
    }
}

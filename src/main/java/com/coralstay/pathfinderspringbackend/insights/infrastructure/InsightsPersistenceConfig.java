package com.coralstay.pathfinderspringbackend.insights.infrastructure;

import com.coralstay.pathfinderspringbackend.common.PersistenceConstants;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
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
        basePackages = PersistenceConstants.INSIGHTS_PACKAGE,
        entityManagerFactoryRef = PersistenceConstants.INSIGHTS_ENTITY_MANAGER_FACTORY,
        transactionManagerRef = PersistenceConstants.INSIGHTS_TRANSACTION_MANAGER
)
public class InsightsPersistenceConfig {

    @Bean
    @ConfigurationProperties(PersistenceConstants.INSIGHTS_DATASOURCE_PROPERTIES)
    public DataSourceProperties insightsDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource insightsDataSource() {
        return insightsDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean(name = PersistenceConstants.INSIGHTS_ENTITY_MANAGER_FACTORY)
    public LocalContainerEntityManagerFactoryBean insightsEntityManagerFactory() {
        var factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(insightsDataSource());
        factory.setPackagesToScan(PersistenceConstants.INSIGHTS_PACKAGE);
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factory.setPersistenceUnitName(PersistenceConstants.INSIGHTS_PERSISTENCE_UNIT);
        return factory;
    }

    @Bean(name = PersistenceConstants.INSIGHTS_TRANSACTION_MANAGER)
    public PlatformTransactionManager insightsTransactionManager(
            EntityManagerFactory insightsEntityManagerFactory) {
        return new JpaTransactionManager(insightsEntityManagerFactory);
    }
}

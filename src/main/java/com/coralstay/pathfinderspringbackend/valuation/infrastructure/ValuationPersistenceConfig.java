package com.coralstay.pathfinderspringbackend.valuation.infrastructure;

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
        basePackages = PersistenceConstants.VALUATION_PACKAGE,
        entityManagerFactoryRef = PersistenceConstants.VALUATION_ENTITY_MANAGER_FACTORY,
        transactionManagerRef = PersistenceConstants.VALUATION_TRANSACTION_MANAGER
)
public class ValuationPersistenceConfig {

    @Bean
    @ConfigurationProperties(PersistenceConstants.VALUATION_DATASOURCE_PROPERTIES)
    public DataSourceProperties valuationDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource valuationDataSource() {
        return valuationDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean(name = PersistenceConstants.VALUATION_ENTITY_MANAGER_FACTORY)
    public LocalContainerEntityManagerFactoryBean valuationEntityManagerFactory() {
        var factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(valuationDataSource());
        factory.setPackagesToScan(PersistenceConstants.VALUATION_PACKAGE);
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factory.setPersistenceUnitName(PersistenceConstants.VALUATION_PERSISTENCE_UNIT);
        return factory;
    }

    @Bean(name = PersistenceConstants.VALUATION_TRANSACTION_MANAGER)
    public PlatformTransactionManager valuationTransactionManager(
            EntityManagerFactory valuationEntityManagerFactory) {
        return new JpaTransactionManager(valuationEntityManagerFactory);
    }
}

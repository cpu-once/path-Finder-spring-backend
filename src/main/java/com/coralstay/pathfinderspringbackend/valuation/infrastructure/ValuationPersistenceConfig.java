package com.coralstay.pathfinderspringbackend.valuation.infrastructure;

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
        basePackages = ValuationPersistenceConfig.BASE_PACKAGE,
        entityManagerFactoryRef = "valuationEntityManagerFactory",
        transactionManagerRef = "valuationTransactionManager"
)
public class ValuationPersistenceConfig {

    public static final String BASE_PACKAGE = "com.coralstay.pathfinderspringbackend.valuation";
    public static final String DATASOURCE_PREFIX = "app.datasource.valuation";
    public static final String UNIT_NAME = "valuation";

    @Bean
    @ConfigurationProperties(DATASOURCE_PREFIX)
    public DataSourceProperties valuationDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource valuationDataSource() {
        return valuationDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean valuationEntityManagerFactory() {
        var factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(valuationDataSource());
        factory.setPackagesToScan(BASE_PACKAGE);
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factory.setPersistenceUnitName(UNIT_NAME);
        return factory;
    }

    @Bean
    public PlatformTransactionManager valuationTransactionManager(
            EntityManagerFactory valuationEntityManagerFactory) {
        return new JpaTransactionManager(valuationEntityManagerFactory);
    }
}

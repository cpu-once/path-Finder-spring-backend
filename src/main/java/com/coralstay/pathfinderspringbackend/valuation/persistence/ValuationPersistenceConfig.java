package com.coralstay.pathfinderspringbackend.valuation.persistence;

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
        basePackages = "com.coralstay.pathfinderspringbackend.valuation",
        entityManagerFactoryRef = "valuationEntityManagerFactory",
        transactionManagerRef = "valuationTransactionManager"
)
public class ValuationPersistenceConfig {

    @Bean
    @ConfigurationProperties("app.datasource.valuation")
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
        factory.setPackagesToScan("com.coralstay.pathfinderspringbackend.valuation");
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factory.setPersistenceUnitName("valuation");
        return factory;
    }

    @Bean
    public PlatformTransactionManager valuationTransactionManager(
            EntityManagerFactory valuationEntityManagerFactory) {
        return new JpaTransactionManager(valuationEntityManagerFactory);
    }
}

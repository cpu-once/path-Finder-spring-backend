package com.coralstay.pathfinderspringbackend.insights.persistence;

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
        basePackages = "com.coralstay.pathfinderspringbackend.insights",
        entityManagerFactoryRef = "insightsEntityManagerFactory",
        transactionManagerRef = "insightsTransactionManager"
)
public class InsightsPersistenceConfig {

    @Bean
    @ConfigurationProperties("app.datasource.insights")
    public DataSourceProperties insightsDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource insightsDataSource() {
        return insightsDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean insightsEntityManagerFactory() {
        var factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(insightsDataSource());
        factory.setPackagesToScan("com.coralstay.pathfinderspringbackend.insights");
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factory.setPersistenceUnitName("insights");
        return factory;
    }

    @Bean
    public PlatformTransactionManager insightsTransactionManager(
            EntityManagerFactory insightsEntityManagerFactory) {
        return new JpaTransactionManager(insightsEntityManagerFactory);
    }
}

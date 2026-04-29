package com.coralstay.pathfinderspringbackend.insights.infrastructure;

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
        basePackages = InsightsPersistenceConfig.BASE_PACKAGE,
        entityManagerFactoryRef = "insightsEntityManagerFactory",
        transactionManagerRef = "insightsTransactionManager"
)
public class InsightsPersistenceConfig {

    public static final String BASE_PACKAGE = "com.coralstay.pathfinderspringbackend.insights";
    public static final String DATASOURCE_PREFIX = "app.datasource.insights";
    public static final String UNIT_NAME = "insights";

    @Bean
    @ConfigurationProperties(DATASOURCE_PREFIX)
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
        factory.setPackagesToScan(BASE_PACKAGE);
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factory.setPersistenceUnitName(UNIT_NAME);
        return factory;
    }

    @Bean
    public PlatformTransactionManager insightsTransactionManager(
            EntityManagerFactory insightsEntityManagerFactory) {
        return new JpaTransactionManager(insightsEntityManagerFactory);
    }
}

package com.coralstay.pathfinderspringbackend.baseline.infrastructure;

import javax.sql.DataSource;

import jakarta.persistence.EntityManagerFactory;

import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(
        basePackages = BaselinePersistenceConfig.BASE_PACKAGE,
        entityManagerFactoryRef = "baselineEntityManagerFactory",
        transactionManagerRef = "baselineTransactionManager"
)
public class BaselinePersistenceConfig {

    public static final String BASE_PACKAGE = "com.coralstay.pathfinderspringbackend.baseline";
    public static final String DATASOURCE_PREFIX = "app.datasource.baseline";
    public static final String UNIT_NAME = "baseline";

    @Primary
    @Bean
    @ConfigurationProperties(DATASOURCE_PREFIX)
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
    @Bean
    public LocalContainerEntityManagerFactoryBean baselineEntityManagerFactory() {
        var factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(baselineDataSource());
        factory.setPackagesToScan(BASE_PACKAGE);
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factory.setPersistenceUnitName(UNIT_NAME);
        return factory;
    }

    @Primary
    @Bean
    public PlatformTransactionManager baselineTransactionManager(
            EntityManagerFactory baselineEntityManagerFactory) {
        return new JpaTransactionManager(baselineEntityManagerFactory);
    }
}

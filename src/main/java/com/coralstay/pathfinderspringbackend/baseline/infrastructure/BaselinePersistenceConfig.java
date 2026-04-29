package com.coralstay.pathfinderspringbackend.baseline.infrastructure;

import com.coralstay.pathfinderspringbackend.common.PersistenceConstants;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
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
        basePackages = PersistenceConstants.BASELINE_PACKAGE,
        entityManagerFactoryRef = PersistenceConstants.BASELINE_ENTITY_MANAGER_FACTORY,
        transactionManagerRef = PersistenceConstants.BASELINE_TRANSACTION_MANAGER
)
public class BaselinePersistenceConfig {

    @Primary
    @Bean
    @ConfigurationProperties(PersistenceConstants.BASELINE_DATASOURCE_PROPERTIES)
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
    @Bean(name = PersistenceConstants.BASELINE_ENTITY_MANAGER_FACTORY)
    public LocalContainerEntityManagerFactoryBean baselineEntityManagerFactory() {
        var factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(baselineDataSource());
        factory.setPackagesToScan(PersistenceConstants.BASELINE_PACKAGE);
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factory.setPersistenceUnitName(PersistenceConstants.BASELINE_PERSISTENCE_UNIT);
        return factory;
    }

    @Primary
    @Bean(name = PersistenceConstants.BASELINE_TRANSACTION_MANAGER)
    public PlatformTransactionManager baselineTransactionManager(
            EntityManagerFactory baselineEntityManagerFactory) {
        return new JpaTransactionManager(baselineEntityManagerFactory);
    }
}

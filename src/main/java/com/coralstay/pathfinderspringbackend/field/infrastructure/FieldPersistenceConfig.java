package com.coralstay.pathfinderspringbackend.field.infrastructure;

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
        basePackages = PersistenceConstants.FIELD_PACKAGE,
        entityManagerFactoryRef = PersistenceConstants.FIELD_ENTITY_MANAGER_FACTORY,
        transactionManagerRef = PersistenceConstants.FIELD_TRANSACTION_MANAGER
)
public class FieldPersistenceConfig {

    @Bean
    @ConfigurationProperties(PersistenceConstants.FIELD_DATASOURCE_PROPERTIES)
    public DataSourceProperties fieldDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource fieldDataSource() {
        return fieldDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean(name = PersistenceConstants.FIELD_ENTITY_MANAGER_FACTORY)
    public LocalContainerEntityManagerFactoryBean fieldEntityManagerFactory() {
        var factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(fieldDataSource());
        factory.setPackagesToScan(PersistenceConstants.FIELD_PACKAGE);
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factory.setPersistenceUnitName(PersistenceConstants.FIELD_PERSISTENCE_UNIT);
        return factory;
    }

    @Bean(name = PersistenceConstants.FIELD_TRANSACTION_MANAGER)
    public PlatformTransactionManager fieldTransactionManager(
            EntityManagerFactory fieldEntityManagerFactory) {
        return new JpaTransactionManager(fieldEntityManagerFactory);
    }
}

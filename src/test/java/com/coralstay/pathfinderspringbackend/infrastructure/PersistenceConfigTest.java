package com.coralstay.pathfinderspringbackend.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import jakarta.persistence.EntityManagerFactory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;

@SpringBootTest
class PersistenceConfigTest {

    private static final String[] MODULES = {"baseline", "field", "insights", "progress", "valuation"};

    @Autowired
    private ApplicationContext context;

    @ParameterizedTest(name = "{0} 모듈 빈 등록 검증")
    @ValueSource(strings = {"baseline", "field", "insights", "progress", "valuation"})
    @DisplayName("각 모듈의 DataSource, EntityManagerFactory, TransactionManager 빈이 등록되어야 한다")
    void allBeansRegistered(String module) {
        assertThat(context.containsBean(module + "DataSource")).isTrue();
        assertThat(context.containsBean(module + "EntityManagerFactory")).isTrue();
        assertThat(context.containsBean(module + "TransactionManager")).isTrue();
    }

    @ParameterizedTest(name = "{0} 모듈 DB 연결 검증")
    @ValueSource(strings = {"baseline", "field", "insights", "progress", "valuation"})
    @DisplayName("각 모듈의 DataSource가 실제로 연결 가능해야 한다")
    void dataSourceConnectionIsValid(String module) throws SQLException {
        DataSource ds = context.getBean(module + "DataSource", DataSource.class);
        try (Connection conn = ds.getConnection()) {
            assertThat(conn.isValid(1)).isTrue();
        }
    }

    @Test
    @DisplayName("5개 모듈의 DataSource가 서로 다른 DB를 가리켜야 한다")
    void dataSourcesPointToDifferentDatabases() throws SQLException {
        Map<String, String> urls = new java.util.LinkedHashMap<>();

        for (String module : MODULES) {
            DataSource ds = context.getBean(module + "DataSource", DataSource.class);
            try (Connection conn = ds.getConnection()) {
                urls.put(module, conn.getMetaData().getURL());
            }
        }

        assertThat(urls.values()).doesNotHaveDuplicates();
    }

    @Test
    @DisplayName("baseline 모듈의 빈이 @Primary로 등록되어야 한다")
    void baselineIsPrimary() {
        DataSource primaryDs = context.getBean(DataSource.class);
        DataSource baselineDs = context.getBean("baselineDataSource", DataSource.class);
        assertThat(primaryDs).isSameAs(baselineDs);

        EntityManagerFactory primaryEmf = context.getBean(EntityManagerFactory.class);
        EntityManagerFactory baselineEmf = context.getBean("baselineEntityManagerFactory", EntityManagerFactory.class);
        assertThat(primaryEmf).isSameAs(baselineEmf);

        PlatformTransactionManager primaryTm = context.getBean(PlatformTransactionManager.class);
        PlatformTransactionManager baselineTm = context.getBean("baselineTransactionManager", PlatformTransactionManager.class);
        assertThat(primaryTm).isSameAs(baselineTm);
    }
}

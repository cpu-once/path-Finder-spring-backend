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

    private static final String MODULE_BASELINE = "baseline";
    private static final String MODULE_FIELD = "field";
    private static final String MODULE_INSIGHTS = "insights";
    private static final String MODULE_PROGRESS = "progress";
    private static final String MODULE_VALUATION = "valuation";

    private static final String[] MODULES = {MODULE_BASELINE, MODULE_FIELD, MODULE_INSIGHTS, MODULE_PROGRESS, MODULE_VALUATION};

    private static final String DATASOURCE_SUFFIX = "DataSource";
    private static final String EMF_SUFFIX = "EntityManagerFactory";
    private static final String TM_SUFFIX = "TransactionManager";

    private static final String BASELINE_DATASOURCE_BEAN = MODULE_BASELINE + DATASOURCE_SUFFIX;
    private static final String BASELINE_EMF_BEAN = MODULE_BASELINE + EMF_SUFFIX;
    private static final String BASELINE_TM_BEAN = MODULE_BASELINE + TM_SUFFIX;

    private static final String PARAM_TEST_NAME_BEAN_REG = "{0} 모듈 빈 등록 검증";
    private static final String DISPLAY_NAME_BEAN_REG = "각 모듈의 DataSource, EntityManagerFactory, TransactionManager 빈이 등록되어야 한다";

    private static final String PARAM_TEST_NAME_DB_CONN = "{0} 모듈 DB 연결 검증";
    private static final String DISPLAY_NAME_DB_CONN = "각 모듈의 DataSource가 실제로 연결 가능해야 한다";

    private static final String DISPLAY_NAME_UNIQUE_DB = "5개 모듈의 DataSource가 서로 다른 DB를 가리켜야 한다";
    private static final String DISPLAY_NAME_PRIMARY = "baseline 모듈의 빈이 @Primary로 등록되어야 한다";

    @Autowired
    private ApplicationContext context;

    @ParameterizedTest(name = PARAM_TEST_NAME_BEAN_REG)
    @ValueSource(strings = {MODULE_BASELINE, MODULE_FIELD, MODULE_INSIGHTS, MODULE_PROGRESS, MODULE_VALUATION})
    @DisplayName(DISPLAY_NAME_BEAN_REG)
    void allBeansRegistered(String module) {
        assertThat(context.containsBean(module + DATASOURCE_SUFFIX)).isTrue();
        assertThat(context.containsBean(module + EMF_SUFFIX)).isTrue();
        assertThat(context.containsBean(module + TM_SUFFIX)).isTrue();
    }

    @ParameterizedTest(name = PARAM_TEST_NAME_DB_CONN)
    @ValueSource(strings = {MODULE_BASELINE, MODULE_FIELD, MODULE_INSIGHTS, MODULE_PROGRESS, MODULE_VALUATION})
    @DisplayName(DISPLAY_NAME_DB_CONN)
    void dataSourceConnectionIsValid(String module) throws SQLException {
        DataSource ds = context.getBean(module + DATASOURCE_SUFFIX, DataSource.class);
        try (Connection conn = ds.getConnection()) {
            assertThat(conn.isValid(1)).isTrue();
        }
    }

    @Test
    @DisplayName(DISPLAY_NAME_UNIQUE_DB)
    void dataSourcesPointToDifferentDatabases() throws SQLException {
        Map<String, String> urls = new java.util.LinkedHashMap<>();

        for (String module : MODULES) {
            DataSource ds = context.getBean(module + DATASOURCE_SUFFIX, DataSource.class);
            try (Connection conn = ds.getConnection()) {
                urls.put(module, conn.getMetaData().getURL());
            }
        }

        assertThat(urls.values()).doesNotHaveDuplicates();
    }

    @Test
    @DisplayName(DISPLAY_NAME_PRIMARY)
    void baselineIsPrimary() {
        DataSource primaryDs = context.getBean(DataSource.class);
        DataSource baselineDs = context.getBean(BASELINE_DATASOURCE_BEAN, DataSource.class);
        assertThat(primaryDs).isSameAs(baselineDs);

        EntityManagerFactory primaryEmf = context.getBean(EntityManagerFactory.class);
        EntityManagerFactory baselineEmf = context.getBean(BASELINE_EMF_BEAN, EntityManagerFactory.class);
        assertThat(primaryEmf).isSameAs(baselineEmf);

        PlatformTransactionManager primaryTm = context.getBean(PlatformTransactionManager.class);
        PlatformTransactionManager baselineTm = context.getBean(BASELINE_TM_BEAN, PlatformTransactionManager.class);
        assertThat(primaryTm).isSameAs(baselineTm);
    }
}

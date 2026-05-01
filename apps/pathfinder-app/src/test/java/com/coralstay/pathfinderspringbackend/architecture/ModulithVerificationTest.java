package com.coralstay.pathfinderspringbackend.architecture;

import com.coralstay.pathfinderspringbackend.PathFinderSpringBackendApplication;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

public class ModulithVerificationTest {

    private static final String DESC_VERIFY_MODULE_COUNT = "모듈 개수 검증: baseline, field, progress, valuation, insights";
    private static final String MSG_MODULE_COUNT_ASSERTION = "인식된 모듈의 개수가 기대와 다릅니다.";

    private static final String DESC_VERIFY_MODULE_STRUCTURE = "모듈 구조 검증: 순환 의존 및 허용되지 않은 의존성 없어야 한다";

    private static final String DESC_LOG_MODULE_STRUCTURE = "모듈 구조 로깅";
    private static final String FMT_MODULE_LOG = "모듈명: %s, 기본 패키지: %s%n";

    private static ApplicationModules modules;

    @BeforeAll
    static void setup() {
        modules = ApplicationModules.of(PathFinderSpringBackendApplication.class);
    }

    @Test
    @DisplayName(DESC_VERIFY_MODULE_COUNT)
    void verifyModuleCount() {
        long count = modules.stream().count();
        Assertions.assertThat(count).as(MSG_MODULE_COUNT_ASSERTION).isEqualTo(5);
    }

    @Test
    @DisplayName(DESC_VERIFY_MODULE_STRUCTURE)
    void verifyModules() {
        modules.verify();
    }

    @Test
    @DisplayName(DESC_LOG_MODULE_STRUCTURE)
    void logModuleStructure() {
        modules.forEach(module ->
                System.out.printf(FMT_MODULE_LOG,
                        module.getDisplayName(), module.getBasePackage()));
    }
}

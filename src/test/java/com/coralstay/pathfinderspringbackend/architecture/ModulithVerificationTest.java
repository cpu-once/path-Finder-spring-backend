package com.coralstay.pathfinderspringbackend.architecture;

import com.coralstay.pathfinderspringbackend.PathFinderSpringBackendApplication;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

public class ModulithVerificationTest {

    private static ApplicationModules modules;

    @BeforeAll
    static void setup() {
        modules = ApplicationModules.of(PathFinderSpringBackendApplication.class);
    }

    @Test
    @DisplayName("모듈 개수 검증: baseline, field, progress, valuation, insights")
    void verifyModuleCount() {
        long count = modules.stream().count();
        Assertions.assertThat(count).as("인식된 모듈의 개수가 기대와 다릅니다.").isEqualTo(5);
    }

    @Test
    @DisplayName("모듈 구조 검증: 순환 의존 및 허용되지 않은 의존성 없어야 한다")
    void verifyModules() {
        modules.verify();
    }

    @Test
    @DisplayName("모듈 구조 로깅")
    void logModuleStructure() {
        modules.forEach(module ->
                System.out.printf("모듈명: %s, 기본 패키지: %s%n",
                        module.getDisplayName(), module.getBasePackage()));
    }
}

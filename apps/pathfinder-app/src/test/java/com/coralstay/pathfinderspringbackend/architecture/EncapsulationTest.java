package com.coralstay.pathfinderspringbackend.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

public class EncapsulationTest {

    private static JavaClasses classes;

    @BeforeAll
    static void setup() {
        classes = new ClassFileImporter().importPackages("com.coralstay.pathfinderspringbackend");
    }

    @Test
    @DisplayName("Entity와 Repository는 외부 모듈에서 직접 접근하지 못하도록 package-private 제한을 권장한다")
    void repositoriesAndEntitiesShouldBePackagePrivate() {
        // 단, Spring Boot의 Entity/Repository 특성상 protected/public이 필요할 수도 있지만,
        // 모듈 분리를 철저히 하기 위해 최소한 UseCase/Service를 제외한
        // 핵심 인프라 객체들은 가능하면 외부에 열리지 않도록 관리하는 것이 좋습니다.

        classes()
                .that().haveNameMatching(".*Repository")
                .or().haveNameMatching(".*Entity")
                .should().bePackagePrivate()
                .orShould().beProtected()
                .check(classes);
    }
}

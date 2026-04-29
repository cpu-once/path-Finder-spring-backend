package com.coralstay.pathfinderspringbackend.baseline.architecture;

import com.coralstay.pathfinderspringbackend.baseline.infrastructure.BaselinePersistenceConstants;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

public class BaselineArchitectureTest {

    private static JavaClasses classes;

    @BeforeAll
    static void setup() {
        classes = new ClassFileImporter().importPackages("com.coralstay.pathfinderspringbackend");
    }

    @Test
    @DisplayName("Baseline 모듈은 다른 모듈에 의존하지 않아야 한다")
    void baselineShouldNotDependOnOtherModules() {
        noClasses()
                .that().resideInAPackage(BaselinePersistenceConstants.PACKAGE + "..")
                .should().dependOnClassesThat().resideInAnyPackage(
                        "com.coralstay.pathfinderspringbackend.field..",
                        "com.coralstay.pathfinderspringbackend.progress..",
                        "com.coralstay.pathfinderspringbackend.valuation..",
                        "com.coralstay.pathfinderspringbackend.insights.."
                )
                .check(classes);
    }
}

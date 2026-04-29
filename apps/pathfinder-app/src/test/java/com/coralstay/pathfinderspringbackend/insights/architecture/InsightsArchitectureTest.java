package com.coralstay.pathfinderspringbackend.insights.architecture;

import com.coralstay.pathfinderspringbackend.insights.infrastructure.InsightsPersistenceConstants;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

public class InsightsArchitectureTest {

    private static JavaClasses classes;

    @BeforeAll
    static void setup() {
        classes = new ClassFileImporter().importPackages("com.coralstay.pathfinderspringbackend");
    }

    @Test
    @DisplayName("Insights 모듈은 다른 모듈의 의존성을 가질 수 있지만, 역으로 다른 모듈이 Insights 모듈에 의존해서는 안 된다")
    void otherModulesShouldNotDependOnInsights() {
        noClasses()
                .that().resideInAnyPackage(
                        "com.coralstay.pathfinderspringbackend.baseline..",
                        "com.coralstay.pathfinderspringbackend.field..",
                        "com.coralstay.pathfinderspringbackend.progress..",
                        "com.coralstay.pathfinderspringbackend.valuation.."
                )
                .should().dependOnClassesThat().resideInAPackage(InsightsPersistenceConstants.PACKAGE + "..")
                .check(classes);
    }
}

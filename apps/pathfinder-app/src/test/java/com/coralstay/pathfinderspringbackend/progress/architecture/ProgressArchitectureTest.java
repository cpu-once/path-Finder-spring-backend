package com.coralstay.pathfinderspringbackend.progress.architecture;

import com.coralstay.pathfinderspringbackend.progress.infrastructure.ProgressPersistenceConstants;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

public class ProgressArchitectureTest {

    private static JavaClasses classes;

    @BeforeAll
    static void setup() {
        classes = new ClassFileImporter().importPackages("com.coralstay.pathfinderspringbackend");
    }

    @Test
    @DisplayName("Progress 모듈은 Valuation, Insights 모듈에 의존하지 않아야 한다")
    void progressShouldNotDependOnUpperModules() {
        noClasses()
                .that().resideInAPackage(ProgressPersistenceConstants.PACKAGE + "..")
                .should().dependOnClassesThat().resideInAnyPackage(
                        "com.coralstay.pathfinderspringbackend.valuation..",
                        "com.coralstay.pathfinderspringbackend.insights.."
                )
                .check(classes);
    }
}

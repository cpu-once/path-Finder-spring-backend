package com.coralstay.pathfinderspringbackend.field.architecture;

import com.coralstay.pathfinderspringbackend.field.infrastructure.FieldPersistenceConstants;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

public class FieldArchitectureTest {

    private static JavaClasses classes;

    @BeforeAll
    static void setup() {
        classes = new ClassFileImporter().importPackages("com.coralstay.pathfinderspringbackend");
    }

    @Test
    @DisplayName("Field 모듈은 Progress, Valuation, Insights 모듈에 의존하지 않아야 한다")
    void fieldShouldNotDependOnUpperModules() {
        noClasses()
                .that().resideInAPackage(FieldPersistenceConstants.PACKAGE + "..")
                .should().dependOnClassesThat().resideInAnyPackage(
                        "com.coralstay.pathfinderspringbackend.progress..",
                        "com.coralstay.pathfinderspringbackend.valuation..",
                        "com.coralstay.pathfinderspringbackend.insights.."
                )
                .check(classes);
    }
}

package com.coralstay.pathfinderspringbackend.valuation.architecture;

import com.coralstay.pathfinderspringbackend.valuation.infrastructure.ValuationPersistenceConstants;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

public class ValuationArchitectureTest {

    private static JavaClasses classes;

    @BeforeAll
    static void setup() {
        classes = new ClassFileImporter().importPackages("com.coralstay.pathfinderspringbackend");
    }

    @Test
    @DisplayName("Valuation 모듈은 Insights 모듈에 의존하지 않아야 한다")
    void valuationShouldNotDependOnUpperModules() {
        noClasses()
                .that().resideInAPackage(ValuationPersistenceConstants.PACKAGE + "..")
                .should().dependOnClassesThat().resideInAnyPackage(
                        "com.coralstay.pathfinderspringbackend.insights.."
                )
                .check(classes);
    }
}

package com.coralstay.pathfinderspringbackend.valuation.architecture;

import com.coralstay.pathfinderspringbackend.valuation.infrastructure.ValuationPersistenceConstants;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

public class ValuationArchitectureTest {

    private static final String ROOT_PACKAGE = "com.coralstay.pathfinderspringbackend";
    private static final String DESC_VALUATION_SHOULD_NOT_DEPEND_ON_UPPER_MODULES = "Valuation 모듈은 Insights 모듈에 의존하지 않아야 한다";
    private static final String PACKAGE_SUFFIX = "..";

    private static final String INSIGHTS_PACKAGE = "com.coralstay.pathfinderspringbackend.insights..";

    private static JavaClasses classes;

    @BeforeAll
    static void setup() {
        classes = new ClassFileImporter().importPackages(ROOT_PACKAGE);
    }

    @Test
    @DisplayName(DESC_VALUATION_SHOULD_NOT_DEPEND_ON_UPPER_MODULES)
    void valuationShouldNotDependOnUpperModules() {
        noClasses()
                .that().resideInAPackage(ValuationPersistenceConstants.PACKAGE + PACKAGE_SUFFIX)
                .should().dependOnClassesThat().resideInAnyPackage(
                        INSIGHTS_PACKAGE
                )
                .check(classes);
    }
}

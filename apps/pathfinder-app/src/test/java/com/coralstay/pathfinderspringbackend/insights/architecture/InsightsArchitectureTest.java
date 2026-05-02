package com.coralstay.pathfinderspringbackend.insights.architecture;

import com.coralstay.pathfinderspringbackend.insights.infrastructure.InsightsPersistenceConstants;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

public class InsightsArchitectureTest {

    private static final String ROOT_PACKAGE = "com.coralstay.pathfinderspringbackend";
    private static final String DESC_INSIGHTS_SHOULD_NOT_DEPEND_ON_VALUATION = "Insights 모듈은 Valuation 모듈에 의존하지 않아야 한다";
    private static final String PACKAGE_SUFFIX = "..";

    private static final String VALUATION_PACKAGE = "com.coralstay.pathfinderspringbackend.valuation..";

    private static JavaClasses classes;

    @BeforeAll
    static void setup() {
        classes = new ClassFileImporter().importPackages(ROOT_PACKAGE);
    }

    @Test
    @DisplayName(DESC_INSIGHTS_SHOULD_NOT_DEPEND_ON_VALUATION)
    void insightsShouldNotDependOnValuation() {
        noClasses()
                .that().resideInAPackage(InsightsPersistenceConstants.PACKAGE + PACKAGE_SUFFIX)
                .should().dependOnClassesThat().resideInAnyPackage(
                        VALUATION_PACKAGE
                )
                .check(classes);
    }
}

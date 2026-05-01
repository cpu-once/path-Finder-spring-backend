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
    private static final String DESC_OTHER_MODULES_SHOULD_NOT_DEPEND_ON_INSIGHTS = "Insights 모듈은 다른 모듈의 의존성을 가질 수 있지만, 역으로 다른 모듈이 Insights 모듈에 의존해서는 안 된다";
    private static final String PACKAGE_SUFFIX = "..";

    private static final String BASELINE_PACKAGE = "com.coralstay.pathfinderspringbackend.baseline..";
    private static final String FIELD_PACKAGE = "com.coralstay.pathfinderspringbackend.field..";
    private static final String PROGRESS_PACKAGE = "com.coralstay.pathfinderspringbackend.progress..";
    private static final String VALUATION_PACKAGE = "com.coralstay.pathfinderspringbackend.valuation..";

    private static JavaClasses classes;

    @BeforeAll
    static void setup() {
        classes = new ClassFileImporter().importPackages(ROOT_PACKAGE);
    }

    @Test
    @DisplayName(DESC_OTHER_MODULES_SHOULD_NOT_DEPEND_ON_INSIGHTS)
    void otherModulesShouldNotDependOnInsights() {
        noClasses()
                .that().resideInAnyPackage(
                        BASELINE_PACKAGE,
                        FIELD_PACKAGE,
                        PROGRESS_PACKAGE,
                        VALUATION_PACKAGE
                )
                .should().dependOnClassesThat().resideInAPackage(InsightsPersistenceConstants.PACKAGE + PACKAGE_SUFFIX)
                .check(classes);
    }
}

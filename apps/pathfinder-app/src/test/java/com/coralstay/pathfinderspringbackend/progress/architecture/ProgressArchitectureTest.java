package com.coralstay.pathfinderspringbackend.progress.architecture;

import com.coralstay.pathfinderspringbackend.progress.infrastructure.ProgressPersistenceConstants;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

public class ProgressArchitectureTest {

    private static final String ROOT_PACKAGE = "com.coralstay.pathfinderspringbackend";
    private static final String DESC_PROGRESS_SHOULD_NOT_DEPEND_ON_UPPER_MODULES = "Progress 모듈은 Valuation, Insights 모듈에 의존하지 않아야 한다";
    private static final String PACKAGE_SUFFIX = "..";

    private static final String VALUATION_PACKAGE = "com.coralstay.pathfinderspringbackend.valuation..";
    private static final String INSIGHTS_PACKAGE = "com.coralstay.pathfinderspringbackend.insights..";

    private static JavaClasses classes;

    @BeforeAll
    static void setup() {
        classes = new ClassFileImporter().importPackages(ROOT_PACKAGE);
    }

    @Test
    @DisplayName(DESC_PROGRESS_SHOULD_NOT_DEPEND_ON_UPPER_MODULES)
    void progressShouldNotDependOnUpperModules() {
        noClasses()
                .that().resideInAPackage(ProgressPersistenceConstants.PACKAGE + PACKAGE_SUFFIX)
                .should().dependOnClassesThat().resideInAnyPackage(
                        VALUATION_PACKAGE,
                        INSIGHTS_PACKAGE
                )
                .check(classes);
    }
}

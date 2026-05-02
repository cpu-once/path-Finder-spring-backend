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
    private static final String DESC_OTHER_MODULES_SHOULD_NOT_DEPEND_ON_VALUATION = "Valuation 모듈은 최상위 모듈이므로, 하위의 다른 모듈들이 Valuation 모듈에 의존해서는 안 된다";
    private static final String PACKAGE_SUFFIX = "..";

    private static final String BASELINE_PACKAGE = "com.coralstay.pathfinderspringbackend.baseline..";
    private static final String FIELD_PACKAGE = "com.coralstay.pathfinderspringbackend.field..";
    private static final String PROGRESS_PACKAGE = "com.coralstay.pathfinderspringbackend.progress..";
    private static final String INSIGHTS_PACKAGE = "com.coralstay.pathfinderspringbackend.insights..";

    private static JavaClasses classes;

    @BeforeAll
    static void setup() {
        classes = new ClassFileImporter().importPackages(ROOT_PACKAGE);
    }

    @Test
    @DisplayName(DESC_OTHER_MODULES_SHOULD_NOT_DEPEND_ON_VALUATION)
    void otherModulesShouldNotDependOnValuation() {
        noClasses()
                .that().resideInAnyPackage(
                        BASELINE_PACKAGE,
                        FIELD_PACKAGE,
                        PROGRESS_PACKAGE,
                        INSIGHTS_PACKAGE
                )
                .should().dependOnClassesThat().resideInAPackage(ValuationPersistenceConstants.PACKAGE + PACKAGE_SUFFIX)
                .check(classes);
    }
}

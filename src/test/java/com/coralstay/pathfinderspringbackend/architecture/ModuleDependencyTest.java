package com.coralstay.pathfinderspringbackend.architecture;

import com.coralstay.pathfinderspringbackend.common.PersistenceConstants;
import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClass.Predicates;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.library.Architectures;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ModuleDependencyTest {

    private static final String ROOT = "com.coralstay.pathfinderspringbackend";

    private static JavaClasses classes;

    @BeforeAll
    static void setup() {
        classes = new ClassFileImporter().importPackages(ROOT);
    }

    @Test
    @DisplayName("단방향 파이프라인 의존성을 위반하면 안 된다")
    void unidirectionalPipeline() {
        DescribedPredicate<JavaClass> isInternal = Predicates.resideInAPackage(ROOT + "..");
        DescribedPredicate<JavaClass> isExternal = DescribedPredicate.not(isInternal);

        Architectures.layeredArchitecture()
                .consideringAllDependencies()
                .layer("Baseline").definedBy(PersistenceConstants.BASELINE_PACKAGE + "..")
                .layer("Field").definedBy(PersistenceConstants.FIELD_PACKAGE + "..")
                .layer("Progress").definedBy(PersistenceConstants.PROGRESS_PACKAGE + "..")
                .layer("Valuation").definedBy(PersistenceConstants.VALUATION_PACKAGE + "..")
                .layer("Insights").definedBy(PersistenceConstants.INSIGHTS_PACKAGE + "..")

                .whereLayer("Baseline").mayNotAccessAnyLayer()
                .whereLayer("Field").mayOnlyAccessLayers("Baseline")
                .whereLayer("Progress").mayOnlyAccessLayers("Baseline", "Field")
                .whereLayer("Valuation").mayOnlyAccessLayers("Baseline", "Field", "Progress")
                .whereLayer("Insights").mayOnlyAccessLayers("Baseline", "Field", "Progress", "Valuation")
                .ignoreDependency(isInternal, isExternal)
                .check(classes);
    }
}

package com.coralstay.pathfinderspringbackend.architecture;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClass.Predicates;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.library.Architectures;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

// TODO: Refactor hardcoded string values to dynamic constants (ArchUnit constants, runtime classpath scanning)
public class ModuleDependencyTest {

    private static JavaClasses classes;

    @BeforeAll
    static void setup() {
        classes = new ClassFileImporter()
                .importPackages("com.coralstay.pathfinderspringbackend");
    }

    @Test
    @DisplayName("단방향 파이프라인 의존성을 위반하면 안 된다")
    void unidirectionalPipeline() {
        DescribedPredicate<JavaClass> isInternal = Predicates.resideInAPackage(
                "com.coralstay.pathfinderspringbackend..");
        DescribedPredicate<JavaClass> isExternal = DescribedPredicate.not(isInternal);

        Architectures.layeredArchitecture()
                .consideringAllDependencies()
                .layer("Baseline").definedBy("com.coralstay.pathfinderspringbackend.baseline..")
                .layer("Field").definedBy("com.coralstay.pathfinderspringbackend.field..")
                .layer("Progress").definedBy("com.coralstay.pathfinderspringbackend.progress..")
                .layer("Valuation").definedBy("com.coralstay.pathfinderspringbackend.valuation..")
                .layer("Insights").definedBy("com.coralstay.pathfinderspringbackend.insights..")

                .whereLayer("Baseline").mayNotAccessAnyLayer()
                .whereLayer("Field").mayOnlyAccessLayers("Baseline")
                .whereLayer("Progress").mayOnlyAccessLayers("Baseline", "Field")
                .whereLayer("Valuation").mayOnlyAccessLayers("Baseline", "Field", "Progress")
                .whereLayer("Insights").mayOnlyAccessLayers("Baseline", "Field", "Progress", "Valuation")
                .ignoreDependency(isInternal, isExternal)
                .check(classes);
    }
}

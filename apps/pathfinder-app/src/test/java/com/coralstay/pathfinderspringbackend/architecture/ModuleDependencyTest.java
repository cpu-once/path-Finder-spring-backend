package com.coralstay.pathfinderspringbackend.architecture;

import com.coralstay.pathfinderspringbackend.baseline.infrastructure.BaselinePersistenceConstants;
import com.coralstay.pathfinderspringbackend.field.infrastructure.FieldPersistenceConstants;
import com.coralstay.pathfinderspringbackend.insights.infrastructure.InsightsPersistenceConstants;
import com.coralstay.pathfinderspringbackend.progress.infrastructure.ProgressPersistenceConstants;
import com.coralstay.pathfinderspringbackend.valuation.infrastructure.ValuationPersistenceConstants;
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
    private static final String PACKAGE_SUFFIX = "..";

    private static final String DESC_UNIDIRECTIONAL_PIPELINE = "단방향 파이프라인 의존성을 위반하면 안 된다 (baseline -> field -> progress -> insights -> valuation)";

    private static final String LAYER_BASELINE = "Baseline";
    private static final String LAYER_FIELD = "Field";
    private static final String LAYER_PROGRESS = "Progress";
    private static final String LAYER_INSIGHTS = "Insights";
    private static final String LAYER_VALUATION = "Valuation";

    private static JavaClasses classes;

    @BeforeAll
    static void setup() {
        classes = new ClassFileImporter().importPackages(ROOT);
    }

    @Test
    @DisplayName(DESC_UNIDIRECTIONAL_PIPELINE)
    void unidirectionalPipeline() {
        DescribedPredicate<JavaClass> isInternal = Predicates.resideInAPackage(ROOT + PACKAGE_SUFFIX);
        DescribedPredicate<JavaClass> isExternal = DescribedPredicate.not(isInternal);

        Architectures.layeredArchitecture()
                .consideringAllDependencies()
                .layer(LAYER_BASELINE).definedBy(BaselinePersistenceConstants.PACKAGE + PACKAGE_SUFFIX)
                .layer(LAYER_FIELD).definedBy(FieldPersistenceConstants.PACKAGE + PACKAGE_SUFFIX)
                .layer(LAYER_PROGRESS).definedBy(ProgressPersistenceConstants.PACKAGE + PACKAGE_SUFFIX)
                .layer(LAYER_INSIGHTS).definedBy(InsightsPersistenceConstants.PACKAGE + PACKAGE_SUFFIX)
                .layer(LAYER_VALUATION).definedBy(ValuationPersistenceConstants.PACKAGE + PACKAGE_SUFFIX)

                .whereLayer(LAYER_BASELINE).mayNotAccessAnyLayer()
                .whereLayer(LAYER_FIELD).mayOnlyAccessLayers(LAYER_BASELINE)
                .whereLayer(LAYER_PROGRESS).mayOnlyAccessLayers(LAYER_BASELINE, LAYER_FIELD)
                .whereLayer(LAYER_INSIGHTS).mayOnlyAccessLayers(LAYER_BASELINE, LAYER_FIELD, LAYER_PROGRESS)
                .whereLayer(LAYER_VALUATION).mayOnlyAccessLayers(LAYER_BASELINE, LAYER_FIELD, LAYER_PROGRESS, LAYER_INSIGHTS)
                .ignoreDependency(isInternal, isExternal)
                .check(classes);
    }
}

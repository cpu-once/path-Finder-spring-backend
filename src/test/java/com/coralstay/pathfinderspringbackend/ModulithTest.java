package com.coralstay.pathfinderspringbackend;

import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.library.Architectures;
import com.tngtech.archunit.core.domain.JavaClass.Predicates;

public class ModulithTest {
    ApplicationModules modules = ApplicationModules.of(PathFinderSpringBackendApplication.class);

    @Test
    @DisplayName("모듈 개수 검증:  Field, Progress, Valuation, Insights 확인")
    void verifyModuleCount() {
    long count = modules.stream().count();
    Assertions.assertThat(count).as("인식된 모듈의 개수가 기대와 다릅니다.").isEqualTo(5);
    }

    @Test
    @DisplayName("모듈 구조 검증")
    void verifyModules() {
        modules.verify();
    }

    @Test
    @DisplayName("어노테이션 누락 및 Coding Agent 사용시 코드삭제, 스파게티 코드를 막기위한 단방향 파이프라인 검증")
    void strictArchitectureGuard() {
    JavaClasses classes = new ClassFileImporter()
            .importPackages("com.coralstay.pathfinderspringbackend");

            DescribedPredicate<JavaClass> isInternal = Predicates.resideInAPackage("com.coralstay.pathfinderspringbackend..");
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

    @Test
    @DisplayName("모듈 구조 로깅")
    void logModuleStructure() {
        modules.forEach(module ->
               System.out.printf("모듈명: %s, 기본 패키지: %s%n",
               module.getDisplayName(), module.getBasePackage()));
    }

    @Test
    @DisplayName("모듈 문서 작성")
    void writeDocumentation() {
        new Documenter(modules)
                .writeModulesAsPlantUml()
                .writeIndividualModulesAsPlantUml()
                .writeModuleCanvases();
    }
}

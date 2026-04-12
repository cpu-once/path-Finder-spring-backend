package com.coralstay.pathfinderspringbackend;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClass.Predicates;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import com.tngtech.archunit.library.Architectures;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

// TODO: Refactor all hardcoded string values to be programmable/dynamic constants.
// TODO: Extract and apply useful advanced rules from the official ArchUnit documentation.
// TODO: Restrict dependencies between Controller -> Service -> Repository -> Domain layers within each package.
// TODO: Enforce strict naming convention constraints across all components.

// TODO: Verify BOM version consistency using the Spring Boot Starter BOM.
// TODO: Define layer-specific structural constraints.
//       (Applies to: DTO, Value Object, Domain Object, Service, Repository, Controller)

// TODO: Restrict the usage of specific external library functions.
// TODO: Add naming and return-type constraints for Spring Data JPA Repository methods.

// TODO: Enforce general Java coding style constraints.
// TODO: Enforce testability constraints (e.g., prohibit hardcoded instantiation of time/randomness).

public class ModulithTest {

  // 모든 테스트 메서드가 공유할 정적 필드
  private static ApplicationModules modules;

  @BeforeAll
  static void setup() {
    modules = ApplicationModules.of(PathFinderSpringBackendApplication.class);
  }


  @Test
  @DisplayName("ArchUnit을 이용한 패키지 구조 및 명명 규약 검증")
  void verifyArchitectureWithArchUnit() {
    JavaClasses importedClasses = new ClassFileImporter()
        .importPackages("com.coralstay.pathfinderspringbackend");

    // 1. 모든 클래스는 소문자로 된 패키지 안에 있어야 함 (Naming Convention)
    ArchRule packageNamingRule = classes()
        .should().resideInAPackage("..")
        .andShould(new ArchCondition<JavaClass>("패키지명은 소문자여야 함") {
          @Override
          public void check(JavaClass item, ConditionEvents events) {
            String packageName = item.getPackageName();
            if (!packageName.equals(packageName.toLowerCase())) {
              events.add(SimpleConditionEvent.violated(item,
                  "패키지명이 대문자를 포함합니다: " + packageName));
            }
          }
        });

    // 2. 모듈 간 단방향 의존성 (우리가 짠 layeredArchitecture와 결합)
    packageNamingRule.check(importedClasses);
  }

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

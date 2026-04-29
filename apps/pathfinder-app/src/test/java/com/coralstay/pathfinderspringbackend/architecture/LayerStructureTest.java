package com.coralstay.pathfinderspringbackend.architecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class LayerStructureTest {

    private static JavaClasses classes;

    @BeforeAll
    static void setup() {
        classes = new ClassFileImporter()
                .importPackages("com.coralstay.pathfinderspringbackend");
    }

    @Nested
    @DisplayName("레이어 격리 제약")
    class LayerIsolationTest {

        @Test
        @DisplayName("entity 패키지 클래스는 같은 모듈의 다른 레이어에 의존하면 안 된다")
        void entityShouldNotDependOnOtherLayers() {
            noClasses()
                    .that().resideInAPackage("..entity..")
                    .should().dependOnClassesThat().resideInAnyPackage(
                            "..infrastructure..", "..simulation..", "..repository..", "..dto..")
                    .allowEmptyShould(true)
                    .check(classes);
        }

        @Test
        @DisplayName("dto 패키지 클래스는 같은 모듈의 다른 레이어에 의존하면 안 된다")
        void dtoShouldNotDependOnOtherLayers() {
            noClasses()
                    .that().resideInAPackage("..dto..")
                    .should().dependOnClassesThat().resideInAnyPackage(
                            "..infrastructure..", "..simulation..", "..repository..", "..entity..")
                    .allowEmptyShould(true)
                    .check(classes);
        }

        @Test
        @DisplayName("repository 패키지는 entity에만 의존해야 한다 (infrastructure, simulation, dto 금지)")
        void repositoryShouldOnlyDependOnEntity() {
            noClasses()
                    .that().resideInAPackage("..repository..")
                    .should().dependOnClassesThat().resideInAnyPackage(
                            "..infrastructure..", "..simulation..", "..dto..")
                    .allowEmptyShould(true)
                    .check(classes);
        }

        @Test
        @DisplayName("simulation 패키지는 infrastructure에 의존하면 안 된다")
        void simulationShouldNotDependOnInfrastructure() {
            noClasses()
                    .that().resideInAPackage("..simulation..")
                    .should().dependOnClassesThat().resideInAPackage("..infrastructure..")
                    .check(classes);
        }
    }

    @Nested
    @DisplayName("Controller → Service → Repository → Domain 의존성 제약")
    class IntraModuleLayerTest {

        @Test
        @DisplayName("Controller는 Service에만 의존해야 한다 (Repository, Entity 직접 접근 금지)")
        void controllerShouldOnlyAccessService() {
            noClasses()
                    .that().resideInAPackage("..controller..")
                    .should().dependOnClassesThat().resideInAnyPackage(
                            "..repository..", "..entity..", "..infrastructure..")
                    .allowEmptyShould(true)
                    .check(classes);
        }

        @Test
        @DisplayName("Service는 Controller에 의존하면 안 된다")
        void serviceShouldNotDependOnController() {
            noClasses()
                    .that().resideInAPackage("..service..")
                    .should().dependOnClassesThat().resideInAPackage("..controller..")
                    .allowEmptyShould(true)
                    .check(classes);
        }

        @Test
        @DisplayName("Repository는 Controller, Service에 의존하면 안 된다")
        void repositoryShouldNotDependOnUpperLayers() {
            noClasses()
                    .that().resideInAPackage("..repository..")
                    .should().dependOnClassesThat().resideInAnyPackage(
                            "..controller..", "..service..")
                    .allowEmptyShould(true)
                    .check(classes);
        }
    }

    @Nested
    @DisplayName("Repository 명명 규약")
    class RepositoryNamingTest {

        @Test
        @DisplayName("Repository 인터페이스는 Repository로 끝나야 한다")
        void repositoryInterfacesShouldEndWithRepository() {
            classes()
                    .that().resideInAPackage("..repository..")
                    .and().areInterfaces()
                    .should().haveSimpleNameEndingWith("Repository")
                    .allowEmptyShould(true)
                    .check(classes);
        }
    }
}

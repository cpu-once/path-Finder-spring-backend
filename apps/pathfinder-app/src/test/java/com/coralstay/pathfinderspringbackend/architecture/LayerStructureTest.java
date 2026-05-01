package com.coralstay.pathfinderspringbackend.architecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class LayerStructureTest {

    private static final String ROOT_PACKAGE = "com.coralstay.pathfinderspringbackend";

    private static final String ENTITY_PACKAGE = "..entity..";
    private static final String DTO_PACKAGE = "..dto..";
    private static final String REPOSITORY_PACKAGE = "..repository..";
    private static final String INFRASTRUCTURE_PACKAGE = "..infrastructure..";
    private static final String SIMULATION_PACKAGE = "..simulation..";
    private static final String CONTROLLER_PACKAGE = "..controller..";
    private static final String SERVICE_PACKAGE = "..service..";

    private static final String REPOSITORY_SUFFIX = "Repository";

    // Display Name Constants
    private static final String DESC_LAYER_ISOLATION_TEST = "레이어 격리 제약";
    private static final String DESC_ENTITY_SHOULD_NOT_DEPEND_ON_OTHER_LAYERS = "entity 패키지 클래스는 같은 모듈의 다른 레이어에 의존하면 안 된다";
    private static final String DESC_DTO_SHOULD_NOT_DEPEND_ON_OTHER_LAYERS = "dto 패키지 클래스는 같은 모듈의 다른 레이어에 의존하면 안 된다";
    private static final String DESC_REPOSITORY_SHOULD_ONLY_DEPEND_ON_ENTITY = "repository 패키지는 entity에만 의존해야 한다 (infrastructure, simulation, dto 금지)";
    private static final String DESC_SIMULATION_SHOULD_NOT_DEPEND_ON_INFRASTRUCTURE = "simulation 패키지는 infrastructure에 의존하면 안 된다";

    private static final String DESC_INTRA_MODULE_LAYER_TEST = "Controller → Service → Repository → Domain 의존성 제약";
    private static final String DESC_CONTROLLER_SHOULD_ONLY_ACCESS_SERVICE = "Controller는 Service에만 의존해야 한다 (Repository, Entity 직접 접근 금지)";
    private static final String DESC_SERVICE_SHOULD_NOT_DEPEND_ON_CONTROLLER = "Service는 Controller에 의존하면 안 된다";
    private static final String DESC_REPOSITORY_SHOULD_NOT_DEPEND_ON_UPPER_LAYERS = "Repository는 Controller, Service에 의존하면 안 된다";

    private static final String DESC_REPOSITORY_NAMING_TEST = "Repository 명명 규약";
    private static final String DESC_REPOSITORY_INTERFACES_SHOULD_END_WITH_REPOSITORY = "Repository 인터페이스는 Repository로 끝나야 한다";

    private static JavaClasses classes;

    @BeforeAll
    static void setup() {
        classes = new ClassFileImporter().importPackages(ROOT_PACKAGE);
    }

    @Nested
    @DisplayName(DESC_LAYER_ISOLATION_TEST)
    class LayerIsolationTest {

        @Test
        @DisplayName(DESC_ENTITY_SHOULD_NOT_DEPEND_ON_OTHER_LAYERS)
        void entityShouldNotDependOnOtherLayers() {
            noClasses()
                    .that().resideInAPackage(ENTITY_PACKAGE)
                    .should().dependOnClassesThat().resideInAnyPackage(
                            INFRASTRUCTURE_PACKAGE, SIMULATION_PACKAGE, REPOSITORY_PACKAGE, DTO_PACKAGE)
                    .allowEmptyShould(true)
                    .check(classes);
        }

        @Test
        @DisplayName(DESC_DTO_SHOULD_NOT_DEPEND_ON_OTHER_LAYERS)
        void dtoShouldNotDependOnOtherLayers() {
            noClasses()
                    .that().resideInAPackage(DTO_PACKAGE)
                    .should().dependOnClassesThat().resideInAnyPackage(
                            INFRASTRUCTURE_PACKAGE, SIMULATION_PACKAGE, REPOSITORY_PACKAGE, ENTITY_PACKAGE)
                    .allowEmptyShould(true)
                    .check(classes);
        }

        @Test
        @DisplayName(DESC_REPOSITORY_SHOULD_ONLY_DEPEND_ON_ENTITY)
        void repositoryShouldOnlyDependOnEntity() {
            noClasses()
                    .that().resideInAPackage(REPOSITORY_PACKAGE)
                    .should().dependOnClassesThat().resideInAnyPackage(
                            INFRASTRUCTURE_PACKAGE, SIMULATION_PACKAGE, DTO_PACKAGE)
                    .allowEmptyShould(true)
                    .check(classes);
        }

        @Test
        @DisplayName(DESC_SIMULATION_SHOULD_NOT_DEPEND_ON_INFRASTRUCTURE)
        void simulationShouldNotDependOnInfrastructure() {
            noClasses()
                    .that().resideInAPackage(SIMULATION_PACKAGE)
                    .should().dependOnClassesThat().resideInAPackage(INFRASTRUCTURE_PACKAGE)
                    .check(classes);
        }
    }

    @Nested
    @DisplayName(DESC_INTRA_MODULE_LAYER_TEST)
    class IntraModuleLayerTest {

        @Test
        @DisplayName(DESC_CONTROLLER_SHOULD_ONLY_ACCESS_SERVICE)
        void controllerShouldOnlyAccessService() {
            noClasses()
                    .that().resideInAPackage(CONTROLLER_PACKAGE)
                    .should().dependOnClassesThat().resideInAnyPackage(
                            REPOSITORY_PACKAGE, ENTITY_PACKAGE, INFRASTRUCTURE_PACKAGE)
                    .allowEmptyShould(true)
                    .check(classes);
        }

        @Test
        @DisplayName(DESC_SERVICE_SHOULD_NOT_DEPEND_ON_CONTROLLER)
        void serviceShouldNotDependOnController() {
            noClasses()
                    .that().resideInAPackage(SERVICE_PACKAGE)
                    .should().dependOnClassesThat().resideInAPackage(CONTROLLER_PACKAGE)
                    .allowEmptyShould(true)
                    .check(classes);
        }

        @Test
        @DisplayName(DESC_REPOSITORY_SHOULD_NOT_DEPEND_ON_UPPER_LAYERS)
        void repositoryShouldNotDependOnUpperLayers() {
            noClasses()
                    .that().resideInAPackage(REPOSITORY_PACKAGE)
                    .should().dependOnClassesThat().resideInAnyPackage(
                            CONTROLLER_PACKAGE, SERVICE_PACKAGE)
                    .allowEmptyShould(true)
                    .check(classes);
        }
    }

    @Nested
    @DisplayName(DESC_REPOSITORY_NAMING_TEST)
    class RepositoryNamingTest {

        @Test
        @DisplayName(DESC_REPOSITORY_INTERFACES_SHOULD_END_WITH_REPOSITORY)
        void repositoryInterfacesShouldEndWithRepository() {
            noClasses()
                    .that().resideInAPackage(REPOSITORY_PACKAGE)
                    .and().areInterfaces()
                    .should().haveSimpleNameEndingWith(REPOSITORY_SUFFIX)
                    .allowEmptyShould(true)
                    .check(classes);
        }
    }
}

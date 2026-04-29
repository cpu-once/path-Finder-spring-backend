package com.coralstay.pathfinderspringbackend.architecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

// TODO: Restrict dependencies between Controller -> Service -> Repository -> Domain layers (엔티티/서비스 생성 후)
// TODO: Add naming and return-type constraints for Spring Data JPA Repository methods (리포지토리 생성 후)
public class LayerStructureTest {

    private static JavaClasses classes;

    @BeforeAll
    static void setup() {
        classes = new ClassFileImporter()
                .importPackages("com.coralstay.pathfinderspringbackend");
    }

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

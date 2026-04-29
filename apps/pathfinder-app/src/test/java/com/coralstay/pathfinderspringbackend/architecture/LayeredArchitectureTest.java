package com.coralstay.pathfinderspringbackend.architecture;

import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.library.Architectures;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

public class LayeredArchitectureTest {

    private static final String ROOT_PACKAGE = "com.coralstay.pathfinderspringbackend";

    @Test
    void testLayeredArchitecture() {
        Architectures.LayeredArchitecture architecture = layeredArchitecture()
                .consideringAllDependencies()
                .layer("Controller").definedBy(ROOT_PACKAGE + "..api..")
                .layer("Service").definedBy(ROOT_PACKAGE + "..service..")
                .layer("Repository").definedBy(ROOT_PACKAGE + "..domain..")
                .layer("Domain").definedBy(ROOT_PACKAGE + "..domain..")
                .layer("Infrastructure").definedBy(ROOT_PACKAGE + "..infrastructure..")

                .whereLayer("Controller").mayOnlyBeAccessedByLayers()
                .whereLayer("Service").mayOnlyBeAccessedByLayers("Controller")
                .whereLayer("Repository").mayOnlyBeAccessedByLayers("Service", "Infrastructure")
                .whereLayer("Domain").mayOnlyBeAccessedByLayers("Repository", "Service", "Controller", "Infrastructure");

        architecture.check(new ClassFileImporter().importPackages(ROOT_PACKAGE));
    }
}

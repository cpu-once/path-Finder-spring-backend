package com.coralstay.pathfinderspringbackend.architecture;

import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.library.Architectures;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

public class LayeredArchitectureTest {

    private static final String ROOT_PACKAGE = "com.coralstay.pathfinderspringbackend";
    private static final String CONTROLLER_LAYER = "Controller";
    private static final String SERVICE_LAYER = "Service";
    private static final String REPOSITORY_LAYER = "Repository";
    private static final String DOMAIN_LAYER = "Domain";
    private static final String INFRASTRUCTURE_LAYER = "Infrastructure";

    private static final String API_PACKAGE = ROOT_PACKAGE + "..api..";
    private static final String SERVICE_PACKAGE = ROOT_PACKAGE + "..service..";
    private static final String DOMAIN_PACKAGE = ROOT_PACKAGE + "..domain..";
    private static final String INFRASTRUCTURE_PACKAGE = ROOT_PACKAGE + "..infrastructure..";

    @Test
    void testLayeredArchitecture() {
        Architectures.LayeredArchitecture architecture = layeredArchitecture()
                .consideringAllDependencies()
                .layer(CONTROLLER_LAYER).definedBy(API_PACKAGE)
                .layer(SERVICE_LAYER).definedBy(SERVICE_PACKAGE)
                .layer(REPOSITORY_LAYER).definedBy(DOMAIN_PACKAGE)
                .layer(DOMAIN_LAYER).definedBy(DOMAIN_PACKAGE)
                .layer(INFRASTRUCTURE_LAYER).definedBy(INFRASTRUCTURE_PACKAGE)

                .whereLayer(CONTROLLER_LAYER).mayOnlyBeAccessedByLayers()
                .whereLayer(SERVICE_LAYER).mayOnlyBeAccessedByLayers(CONTROLLER_LAYER)
                .whereLayer(REPOSITORY_LAYER).mayOnlyBeAccessedByLayers(SERVICE_LAYER, INFRASTRUCTURE_LAYER)
                .whereLayer(DOMAIN_LAYER).mayOnlyBeAccessedByLayers(REPOSITORY_LAYER, SERVICE_LAYER, CONTROLLER_LAYER, INFRASTRUCTURE_LAYER);

        architecture.check(new ClassFileImporter().importPackages(ROOT_PACKAGE));
    }
}

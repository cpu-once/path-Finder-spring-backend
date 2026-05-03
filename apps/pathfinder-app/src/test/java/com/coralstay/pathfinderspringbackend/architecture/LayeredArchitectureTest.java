package com.coralstay.pathfinderspringbackend.architecture;

import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.library.Architectures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

public class LayeredArchitectureTest {

    private static final String ROOT_PACKAGE = "com.coralstay.pathfinderspringbackend";
    private static final String CONTROLLER_LAYER = "Controller";
    private static final String CONSUMER_LAYER = "Consumer";
    private static final String SERVICE_LAYER = "Service";
    private static final String REPOSITORY_LAYER = "Repository";
    private static final String ENTITY_LAYER = "Entity";
    private static final String EVENT_LAYER = "Event";
    private static final String INFRASTRUCTURE_LAYER = "Infrastructure";
    private static final String SIMULATION_LAYER = "Simulation";

    private static final String CONTROLLER_PACKAGE = ROOT_PACKAGE + "..controller..";
    private static final String CONSUMER_PACKAGE = ROOT_PACKAGE + "..consumers..";
    private static final String SERVICE_PACKAGE = ROOT_PACKAGE + "..service..";
    private static final String REPOSITORY_PACKAGE = ROOT_PACKAGE + "..repository..";
    private static final String ENTITY_PACKAGE = ROOT_PACKAGE + "..entity..";
    private static final String EVENT_PACKAGE = ROOT_PACKAGE + "..events..";
    private static final String INFRASTRUCTURE_PACKAGE = ROOT_PACKAGE + "..infrastructure..";
    private static final String SIMULATION_PACKAGE = ROOT_PACKAGE + "..simulation..";

    private static final String DESC_LAYERED_ARCHITECTURE = "Controller, Consumer, Service, Repository, Entity, Event 간의 계층형 아키텍처 제약(EDA 포함)을 준수해야 한다";

    @Test
    @DisplayName(DESC_LAYERED_ARCHITECTURE)
    void testLayeredArchitecture() {
        Architectures.LayeredArchitecture architecture = layeredArchitecture()
                .consideringAllDependencies()
                .layer(CONTROLLER_LAYER).definedBy(CONTROLLER_PACKAGE)
                .layer(CONSUMER_LAYER).definedBy(CONSUMER_PACKAGE)
                .layer(SERVICE_LAYER).definedBy(SERVICE_PACKAGE)
                .layer(REPOSITORY_LAYER).definedBy(REPOSITORY_PACKAGE)
                .layer(ENTITY_LAYER).definedBy(ENTITY_PACKAGE)
                .layer(EVENT_LAYER).definedBy(EVENT_PACKAGE)
                .layer(INFRASTRUCTURE_LAYER).definedBy(INFRASTRUCTURE_PACKAGE)
                .layer(SIMULATION_LAYER).definedBy(SIMULATION_PACKAGE)

                // Controller와 Consumer는 진입점이므로 내부 계층에서 접근할 수 없음
                .whereLayer(CONTROLLER_LAYER).mayOnlyBeAccessedByLayers()
                .whereLayer(CONSUMER_LAYER).mayOnlyBeAccessedByLayers()

                // Service는 진입점(Controller, Consumer) 및 시뮬레이터에서만 접근 가능
                .whereLayer(SERVICE_LAYER).mayOnlyBeAccessedByLayers(CONTROLLER_LAYER, CONSUMER_LAYER, SIMULATION_LAYER)

                // Repository는 Service, Infrastructure, Simulation 등에서 접근 가능
                .whereLayer(REPOSITORY_LAYER).mayOnlyBeAccessedByLayers(SERVICE_LAYER, INFRASTRUCTURE_LAYER, SIMULATION_LAYER)

                // Entity는 가장 하위 도메인이므로 누구나 접근 가능하지만, 주로 Repository와 Service에서 다룸
                .whereLayer(ENTITY_LAYER).mayOnlyBeAccessedByLayers(REPOSITORY_LAYER, SERVICE_LAYER, CONTROLLER_LAYER, CONSUMER_LAYER, INFRASTRUCTURE_LAYER, SIMULATION_LAYER)

                // Event는 모듈 간 통신을 위해 발행(Service) 및 구독(Consumer) 계층에서 모두 접근 가능
                .whereLayer(EVENT_LAYER).mayOnlyBeAccessedByLayers(SERVICE_LAYER, CONSUMER_LAYER, CONTROLLER_LAYER, SIMULATION_LAYER);

        architecture.check(new ClassFileImporter().importPackages(ROOT_PACKAGE));
    }
}

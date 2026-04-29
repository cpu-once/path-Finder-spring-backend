package com.coralstay.pathfinderspringbackend.architecture;

import com.coralstay.pathfinderspringbackend.PathFinderSpringBackendApplication;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

public class DocumentationTest {

    private static ApplicationModules modules;

    @BeforeAll
    static void setup() {
        modules = ApplicationModules.of(PathFinderSpringBackendApplication.class);
    }

    @Test
    @DisplayName("모듈 문서 작성: PlantUML 다이어그램 및 캔버스 생성")
    void writeDocumentation() {
        new Documenter(modules)
                .writeModulesAsPlantUml()
                .writeIndividualModulesAsPlantUml()
                .writeModuleCanvases();
    }
}

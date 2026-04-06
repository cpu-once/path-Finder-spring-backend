package com.coralstay.pathfinderspringbackend;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

public class ModulithTest {
    ApplicationModules modules = ApplicationModules.of(PathFinderSpringBackendApplication.class);

    @Test
    void verifyModules() {
        modules.verify();
    }
}

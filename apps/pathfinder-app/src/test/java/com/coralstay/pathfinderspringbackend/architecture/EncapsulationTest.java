package com.coralstay.pathfinderspringbackend.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

public class EncapsulationTest {

    private static final String ROOT_PACKAGE = "com.coralstay.pathfinderspringbackend";
    private static final String DESC_REPOSITORIES_ENTITIES_PACKAGE_PRIVATE = "Entity와 Repository는 외부 모듈에서 직접 접근하지 못하도록 package-private 제한을 권장한다";
    private static final String REPOSITORY_SUFFIX = ".*Repository";
    private static final String ENTITY_SUFFIX = ".*Entity";

    private static JavaClasses classes;

    @BeforeAll
    static void setup() {
        classes = new ClassFileImporter().importPackages(ROOT_PACKAGE);
    }

    @Test
    @DisplayName(DESC_REPOSITORIES_ENTITIES_PACKAGE_PRIVATE)
    void repositoriesAndEntitiesShouldBePackagePrivate() {
        classes()
                .that().haveNameMatching(REPOSITORY_SUFFIX)
                .or().haveNameMatching(ENTITY_SUFFIX)
                .should().bePackagePrivate()
                .orShould().beProtected()
                .check(classes);
    }
}

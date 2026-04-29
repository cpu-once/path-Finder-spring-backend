package com.coralstay.pathfinderspringbackend.architecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

// TODO: Enforce strict naming convention constraints across all components
// TODO: Enforce naming conventions for test classes and methods
// TODO: Enforce general Java coding style constraints
public class NamingConventionTest {

    private static JavaClasses classes;

    @BeforeAll
    static void setup() {
        classes = new ClassFileImporter()
                .importPackages("com.coralstay.pathfinderspringbackend");
    }

    @Test
    @DisplayName("모든 패키지명은 소문자여야 한다")
    void packageNamesShouldBeLowercase() {
        ArchRule rule = classes()
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
        rule.check(classes);
    }
}

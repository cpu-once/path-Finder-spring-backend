package com.coralstay.pathfinderspringbackend.architecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noFields;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Configuration;

public class NamingConventionTest {

    private static JavaClasses productionClasses;
    private static JavaClasses testClasses;

    @BeforeAll
    static void setup() {
        productionClasses = new ClassFileImporter()
                .importPackages("com.coralstay.pathfinderspringbackend");
        testClasses = new ClassFileImporter()
                .importPaths("build/classes/java/test");
    }

    @Nested
    @DisplayName("패키지 명명 규약")
    class PackageNamingTest {

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
            rule.check(productionClasses);
        }
    }

    @Nested
    @DisplayName("컴포넌트 명명 규약")
    class ComponentNamingTest {

        @Test
        @DisplayName("@Configuration 클래스는 Config로 끝나야 한다")
        void configurationClassesShouldEndWithConfig() {
            classes()
                    .that().areAnnotatedWith(Configuration.class)
                    .should().haveSimpleNameEndingWith("Config")
                    .check(productionClasses);
        }

        @Test
        @DisplayName("@Service 클래스는 Service로 끝나야 한다")
        void serviceClassesShouldEndWithService() {
            classes()
                    .that().areAnnotatedWith(Service.class)
                    .should().haveSimpleNameEndingWith("Service")
                    .allowEmptyShould(true)
                    .check(productionClasses);
        }

        @Test
        @DisplayName("DataSeeder 구현체는 DataSeeder로 끝나야 한다")
        void dataSeederImplementationsShouldEndWithDataSeeder() {
            classes()
                    .that().implement(com.coralstay.pathfinderspringbackend.baseline.simulation.DataSeeder.class)
                    .should().haveSimpleNameEndingWith("DataSeeder")
                    .check(productionClasses);
        }

        @Test
        @DisplayName("인터페이스 이름에 Impl 접두사를 사용하면 안 된다")
        void interfacesShouldNotStartWithI() {
            classes()
                    .that().areInterfaces()
                    .should().haveSimpleNameNotStartingWith("I")
                    .check(productionClasses);
        }
    }

    @Nested
    @DisplayName("테스트 클래스 명명 규약")
    class TestClassNamingTest {

        @Test
        @DisplayName("테스트 클래스는 Test로 끝나야 한다")
        void testClassesShouldEndWithTest() {
            classes()
                    .that().resideInAPackage("com.coralstay.pathfinderspringbackend..")
                    .should().haveSimpleNameEndingWith("Test")
                    .orShould().haveSimpleNameEndingWith("Tests")
                    .check(testClasses);
        }
    }

    @Nested
    @DisplayName("Java 코딩 스타일 제약")
    class CodingStyleTest {

        @Test
        @DisplayName("public 필드는 static final(상수)만 허용한다")
        void noPublicFieldsExceptConstants() {
            noFields()
                    .that().arePublic()
                    .and().areNotStatic()
                    .should().bePrivate()
                    .allowEmptyShould(true)
                    .because("public 인스턴스 필드는 캡슐화를 깨뜨린다. getter/setter 또는 record를 사용해야 한다")
                    .check(productionClasses);
        }

        @Test
        @DisplayName("유틸리티 클래스(모든 메서드가 static)는 인스턴스화를 막아야 한다")
        void utilityClassesShouldNotBeInstantiable() {
            classes()
                    .that().haveSimpleNameEndingWith("Util")
                    .or().haveSimpleNameEndingWith("Utils")
                    .or().haveSimpleNameEndingWith("Helper")
                    .should().haveOnlyFinalFields()
                    .allowEmptyShould(true)
                    .check(productionClasses);
        }
    }
}

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

    private static final String ROOT_PACKAGE = "com.coralstay.pathfinderspringbackend";
    private static final String TEST_CLASSES_PATH = "build/classes/java/test";

    private static final String ALL_PACKAGES = "..";
    private static final String ROOT_PACKAGE_SUFFIX = "com.coralstay.pathfinderspringbackend..";

    private static final String CONFIG_SUFFIX = "Config";
    private static final String SERVICE_SUFFIX = "Service";
    private static final String DATA_SEEDER_SUFFIX = "DataSeeder";
    private static final String INTERFACE_PREFIX = "I";
    private static final String TEST_SUFFIX_1 = "Test";
    private static final String TEST_SUFFIX_2 = "Tests";

    private static final String UTIL_SUFFIX_1 = "Util";
    private static final String UTIL_SUFFIX_2 = "Utils";
    private static final String HELPER_SUFFIX = "Helper";

    private static final String MSG_ERR_UPPERCASE_PACKAGE = "패키지명이 대문자를 포함합니다: ";
    private static final String MSG_COND_LOWERCASE_PACKAGE = "패키지명은 소문자여야 함";
    private static final String MSG_BECAUSE_PUBLIC_FIELDS = "public 인스턴스 필드는 캡슐화를 깨뜨린다. getter/setter 또는 record를 사용해야 한다";

    // Display Name Constants
    private static final String DESC_PACKAGE_NAMING_TEST = "패키지 명명 규약";
    private static final String DESC_PACKAGE_NAMES_SHOULD_BE_LOWERCASE = "모든 패키지명은 소문자여야 한다";

    private static final String DESC_COMPONENT_NAMING_TEST = "컴포넌트 명명 규약";
    private static final String DESC_CONFIGURATION_CLASSES_SHOULD_END_WITH_CONFIG = "@Configuration 클래스는 Config로 끝나야 한다";
    private static final String DESC_SERVICE_CLASSES_SHOULD_END_WITH_SERVICE = "@Service 클래스는 Service로 끝나야 한다";
    private static final String DESC_DATA_SEEDER_IMPLEMENTATIONS_SHOULD_END_WITH_DATA_SEEDER = "DataSeeder 구현체는 DataSeeder로 끝나야 한다";
    private static final String DESC_INTERFACES_SHOULD_NOT_START_WITH_I = "인터페이스 이름에 Impl 접두사를 사용하면 안 된다";

    private static final String DESC_TEST_CLASS_NAMING_TEST = "테스트 클래스 명명 규약";
    private static final String DESC_TEST_CLASSES_SHOULD_END_WITH_TEST = "테스트 클래스는 Test로 끝나야 한다";

    private static final String DESC_CODING_STYLE_TEST = "Java 코딩 스타일 제약";
    private static final String DESC_NO_PUBLIC_FIELDS_EXCEPT_CONSTANTS = "public 필드는 static final(상수)만 허용한다";
    private static final String DESC_UTILITY_CLASSES_SHOULD_NOT_BE_INSTANTIABLE = "유틸리티 클래스(모든 메서드가 static)는 인스턴스화를 막아야 한다";

    private static JavaClasses productionClasses;
    private static JavaClasses testClasses;

    @BeforeAll
    static void setup() {
        productionClasses = new ClassFileImporter().importPackages(ROOT_PACKAGE);
        testClasses = new ClassFileImporter().importPaths(TEST_CLASSES_PATH);
    }

    @Nested
    @DisplayName(DESC_PACKAGE_NAMING_TEST)
    class PackageNamingTest {

        @Test
        @DisplayName(DESC_PACKAGE_NAMES_SHOULD_BE_LOWERCASE)
        void packageNamesShouldBeLowercase() {
            ArchRule rule = classes()
                    .should().resideInAPackage(ALL_PACKAGES)
                    .andShould(new ArchCondition<JavaClass>(MSG_COND_LOWERCASE_PACKAGE) {
                        @Override
                        public void check(JavaClass item, ConditionEvents events) {
                            String packageName = item.getPackageName();
                            if (!packageName.equals(packageName.toLowerCase())) {
                                events.add(SimpleConditionEvent.violated(item, MSG_ERR_UPPERCASE_PACKAGE + packageName));
                            }
                        }
                    });
            rule.check(productionClasses);
        }
    }

    @Nested
    @DisplayName(DESC_COMPONENT_NAMING_TEST)
    class ComponentNamingTest {

        @Test
        @DisplayName(DESC_CONFIGURATION_CLASSES_SHOULD_END_WITH_CONFIG)
        void configurationClassesShouldEndWithConfig() {
            classes()
                    .that().areAnnotatedWith(Configuration.class)
                    .should().haveSimpleNameEndingWith(CONFIG_SUFFIX)
                    .check(productionClasses);
        }

        @Test
        @DisplayName(DESC_SERVICE_CLASSES_SHOULD_END_WITH_SERVICE)
        void serviceClassesShouldEndWithService() {
            classes()
                    .that().areAnnotatedWith(Service.class)
                    .should().haveSimpleNameEndingWith(SERVICE_SUFFIX)
                    .allowEmptyShould(true)
                    .check(productionClasses);
        }

        @Test
        @DisplayName(DESC_DATA_SEEDER_IMPLEMENTATIONS_SHOULD_END_WITH_DATA_SEEDER)
        void dataSeederImplementationsShouldEndWithDataSeeder() {
            classes()
                    .that().implement(com.coralstay.pathfinderspringbackend.baseline.simulation.DataSeeder.class)
                    .should().haveSimpleNameEndingWith(DATA_SEEDER_SUFFIX)
                    .check(productionClasses);
        }

        @Test
        @DisplayName(DESC_INTERFACES_SHOULD_NOT_START_WITH_I)
        void interfacesShouldNotStartWithI() {
            classes()
                    .that().areInterfaces()
                    .should().haveSimpleNameNotStartingWith(INTERFACE_PREFIX)
                    .check(productionClasses);
        }
    }

    @Nested
    @DisplayName(DESC_TEST_CLASS_NAMING_TEST)
    class TestClassNamingTest {

        @Test
        @DisplayName(DESC_TEST_CLASSES_SHOULD_END_WITH_TEST)
        void testClassesShouldEndWithTest() {
            classes()
                    .that().resideInAPackage(ROOT_PACKAGE_SUFFIX)
                    .should().haveSimpleNameEndingWith(TEST_SUFFIX_1)
                    .orShould().haveSimpleNameEndingWith(TEST_SUFFIX_2)
                    .check(testClasses);
        }
    }

    @Nested
    @DisplayName(DESC_CODING_STYLE_TEST)
    class CodingStyleTest {

        @Test
        @DisplayName(DESC_NO_PUBLIC_FIELDS_EXCEPT_CONSTANTS)
        void noPublicFieldsExceptConstants() {
            noFields()
                    .that().arePublic()
                    .and().areNotStatic()
                    .should().bePrivate()
                    .allowEmptyShould(true)
                    .because(MSG_BECAUSE_PUBLIC_FIELDS)
                    .check(productionClasses);
        }

        @Test
        @DisplayName(DESC_UTILITY_CLASSES_SHOULD_NOT_BE_INSTANTIABLE)
        void utilityClassesShouldNotBeInstantiable() {
            classes()
                    .that().haveSimpleNameEndingWith(UTIL_SUFFIX_1)
                    .or().haveSimpleNameEndingWith(UTIL_SUFFIX_2)
                    .or().haveSimpleNameEndingWith(HELPER_SUFFIX)
                    .should().haveOnlyFinalFields()
                    .allowEmptyShould(true)
                    .check(productionClasses);
        }
    }
}

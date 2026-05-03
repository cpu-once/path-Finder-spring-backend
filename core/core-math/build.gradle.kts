plugins {
    java
    `java-library`
}

group = "com.coralstay.core.math"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

dependencies {
    // 순수 자바/수학 라이브러리 (Spring 의존성 배제)
    testImplementation("org.junit.jupiter:junit-jupiter:5.11.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

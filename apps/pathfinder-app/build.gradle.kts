plugins {
  java
  alias(libs.plugins.spring.boot)
  alias(libs.plugins.spring.dependency.management)
  alias(libs.plugins.asciidoctor)
}

group = "com.coralstay"
version = "0.0.1-SNAPSHOT"
description = "pathFinder-app"

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(25)
  }
}

extra["snippetsDir"] = file("build/generated-snippets")

dependencies {
  // 내부 모듈 의존성 추가 (projects.core.math 로 접근)
  implementation(projects.core.math)

  developmentOnly(libs.spring.boot.devtools)

  implementation(libs.spring.modulith.starter.core)
  implementation(libs.spring.modulith.starter.jpa)

  implementation(libs.spring.boot.starter.validation)
  implementation(libs.spring.boot.starter.web)
  implementation(libs.spring.boot.starter.data.jpa)
  implementation(libs.spring.boot.h2console)

  implementation(libs.datafaker)

  compileOnly(libs.lombok)
  runtimeOnly(libs.h2)

  annotationProcessor(libs.spring.boot.configuration.processor)
  annotationProcessor(libs.lombok)

  testImplementation(libs.spring.boot.starter.test)
  testImplementation(libs.spring.boot.starter.webmvc.test)
  testImplementation(libs.spring.modulith.starter.test)
  testImplementation(libs.spring.restdocs.mockmvc)
  testImplementation(libs.archunit.junit5)

  testCompileOnly(libs.lombok)
  // Spring Boot 의존성 관리에 위임
  testRuntimeOnly("org.junit.platform:junit-platform-launcher")
  testAnnotationProcessor(libs.lombok)
}

dependencyManagement {
  imports {
    mavenBom(libs.spring.modulith.bom.get().toString())
  }
}

tasks.withType<Test> {
  useJUnitPlatform()
}

tasks.test {
  outputs.dir(project.extra["snippetsDir"]!!)
}

tasks.asciidoctor {
  inputs.dir(project.extra["snippetsDir"]!!)
  dependsOn(tasks.test)
}

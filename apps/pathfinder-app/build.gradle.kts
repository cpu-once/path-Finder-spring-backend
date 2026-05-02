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

  // Spring Boot Core & Web
  implementation(libs.spring.boot.starter.web)
  implementation(libs.spring.boot.starter.validation)
  implementation(libs.spring.boot.starter.data.jpa)

  // Spring Modulith
  implementation(libs.spring.modulith.starter.core)
  implementation(libs.spring.modulith.starter.jpa)

  // Data Seeding
  implementation(libs.datafaker)

  // 개발 및 런타임 전용 (빌드 결과물에는 포함되거나 동작하지 않음)
  developmentOnly(libs.spring.boot.devtools)
  runtimeOnly(libs.h2)

  // 컴파일 타임 전용 (어노테이션 프로세서 포함)
  compileOnly(libs.lombok)
  annotationProcessor(libs.lombok)
  annotationProcessor(libs.spring.boot.configuration.processor)

  // 테스트 전용 (운영 환경 포함 안 됨)
  testImplementation(libs.spring.boot.starter.test)
  testImplementation(libs.spring.boot.starter.webmvc.test)
  testImplementation(libs.spring.modulith.starter.test)
  testImplementation(libs.spring.restdocs.mockmvc)
  testImplementation(libs.archunit.junit5)

  testCompileOnly(libs.lombok)
  testAnnotationProcessor(libs.lombok)
  testRuntimeOnly("org.junit.platform:junit-platform-launcher")
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

// 스프링 부트 실행(bootRun) 전 반드시 테스트(test)를 먼저 통과하도록 강제하는 설정
tasks.bootRun {
  dependsOn(tasks.test)
}

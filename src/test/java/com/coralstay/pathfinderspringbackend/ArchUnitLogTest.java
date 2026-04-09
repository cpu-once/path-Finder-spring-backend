package com.coralstay.pathfinderspringbackend;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.Test;

public class ArchUnitLogTest {

  @Test
  void logProjectStructure() {
    // 1. 특정 패키지 하위의 모든 클래스를 데이터 스트림으로 읽어옴
    JavaClasses classes = new ClassFileImporter()
        .importPackages("com.coralstay.pathfinderspringbackend");

    // 2. 읽어온 클래스들 로그 찍어보기 (어떤 녀석들이 잡혔나?)
    System.out.println("=== 스캔된 클래스 목록 ===");
    classes.forEach(javaClass -> {
      System.out.printf("클래스명: %s | 패키지: %s | 인터페이스여부: %b%n",
          javaClass.getSimpleName(),
          javaClass.getPackageName(),
          javaClass.isInterface());
    });

    // 3. 특정 조건으로 필터링해서 찍어보기 (예: 'Field' 포함 패키지만)
    System.out.println("\n=== Field 모듈 내부 클래스 ===");
    classes.stream()
        .filter(c -> c.getPackageName().contains(".field"))
        .forEach(c -> System.out.println("Found: " + c.getName()));
  }
}

# Project Backlog

## 1. High Priority: Infrastructure & Architecture Setup
- `[ ]` 디비 하나로 통합하고, 스키마를 모듈별로 분리하기
- `[ ]` Verify BOM version consistency using the Spring Boot Starter BOM.
- `[ ]` Define layer-specific structural constraints. (Applies to: DTO, Value Object, Domain Object, Service, Repository, Controller)
- `[x]` Restrict dependencies between Controller -> Service -> Repository -> Domain layers within each package. *(Completed via `LayeredArchitectureTest` / `EncapsulationTest`)*
- `[x]` Refactor all hardcoded string values to be programmable/dynamic constants. Using ArchUnit constants and properties. Runtime classpath scanning. *(Completed via `PersistenceConstants`)*

## 2. Medium Priority: Core Feature Implementation
- `[ ]` Implement data seeding logic for Baseline (`baseline/simulation/BaselineDataSeeder.java`)
- `[ ]` Implement data seeding logic for Field (`field/simulation/FieldDataSeeder.java`)
- `[ ]` Implement data seeding logic for Insights (`insights/simulation/InsightsDataSeeder.java`)
- `[ ]` Implement data seeding logic for Progress (`progress/simulation/ProgressDataSeeder.java`)
- `[ ]` Implement data seeding logic for Valuation (`valuation/simulation/ValuationDataSeeder.java`)

## 3. Medium Priority: Test Infrastructure & Refactoring
- `[x]` Split file into multiple test classes for better organization and maintainability. *(Completed)*
- `[ ]` Ensure test isolation and clean-up after each test.
- `[ ]` Implement test data setup and teardown using `@BeforeEach` and `@AfterEach`.
- `[ ]` Refactor test classes to follow DRY (Don't Repeat Yourself) principle.
- `[ ]` Make Test hierarchy more readable and maintainable.
- `[ ]` Optimize test execution speed to make them faster and more efficient.
- `[ ]` Use parameterized tests for repetitive test scenarios.
- `[ ]` Enforce testability constraints (e.g., prohibit hardcoded instantiation of time/randomness).

## 4. Low Priority: Code Quality & Conventions Enforcement
- `[ ]` Enforce strict naming convention constraints across all components.
- `[ ]` Enforce naming conventions for test classes and methods.
- `[ ]` Add naming and return-type constraints for Spring Data JPA Repository methods.
- `[ ]` Enforce general Java coding style constraints.
- `[ ]` Restrict the usage of specific external library functions.
- `[ ]` Extract and apply useful advanced rules from the official ArchUnit documentation.

## 5. Low Priority: CI/CD, Telemetry & Security
- `[ ]` Implement test case categorization for different environments (e.g., unit, integration, end-to-end).
- `[ ]` Implement test case prioritization based on business impact.
- `[ ]` Automate test execution and reporting using CI/CD pipelines.
- `[ ]` Implement test coverage checks for critical components.
- `[ ]` Integrate with code review tools for automated feedback.
- `[ ]` Implement automated test result analysis and alerting.
- `[ ]` Apply Mythos to find security vulnerabilities.

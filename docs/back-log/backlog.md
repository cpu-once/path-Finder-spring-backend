# Project Backlog

## Pending TODOs (Source Code)
Below is a list of TODOs found across the main codebase.

- **`apps/pathfinder-app/src/main/java/com/coralstay/pathfinderspringbackend/field/simulation/FieldDataSeeder.java:9`**
  - `// TODO: Implement data seeding logic`
- **`apps/pathfinder-app/src/main/java/com/coralstay/pathfinderspringbackend/insights/simulation/InsightsDataSeeder.java:9`**
  - `// TODO: Implement data seeding logic`
- **`apps/pathfinder-app/src/main/java/com/coralstay/pathfinderspringbackend/progress/simulation/ProgressDataSeeder.java:9`**
  - `// TODO: Implement data seeding logic`
- **`apps/pathfinder-app/src/main/java/com/coralstay/pathfinderspringbackend/valuation/simulation/ValuationDataSeeder.java:9`**
  - `// TODO: Implement data seeding logic`

## Architecture & Test Backlog
These TODOs were originally planned for test infrastructure and architectural enforcement.

### Day 1: Fortification & Core Boundaries
- `[ ]` Apply Mythos to find security vulnerabilities.
- `[ ]` Verify BOM version consistency using the Spring Boot Starter BOM.
- `[x]` Restrict dependencies between Controller -> Service -> Repository -> Domain layers within each package. *(Completed via `LayeredArchitectureTest` / `EncapsulationTest`)*
- `[ ]` Define layer-specific structural constraints. (Applies to: DTO, Value Object, Domain Object, Service, Repository, Controller)

### Day 2: Test Infrastructure Overhaul
- `[x]` Split file into multiple test classes for better organization and maintainability. *(Completed)*
- `[ ]` Ensure test isolation and clean-up after each test.
- `[ ]` Implement test data setup and teardown using @BeforeEach and @AfterEach.
- `[ ]` These test are slow. make it faster and more efficient.
- `[ ]` Make Test hierarchy more readable and maintainable.
- `[ ]` Refactor test classes to follow DRY (Don't Repeat Yourself) principle.

### Day 3: Advanced Programmatic Constraints
- `[x]` Refactor all hardcoded string values to be programmable/dynamic constants. Using ArchUnit constants and properties. Runtime classpath scanning. *(Completed via `PersistenceConstants`)*
- `[ ]` Restrict the usage of specific external library functions.
- `[ ]` Enforce testability constraints (e.g., prohibit hardcoded instantiation of time/randomness).
- `[ ]` Extract and apply useful advanced rules from the official ArchUnit documentation.

### Day 4: Conventions, Aesthetics, & Test Categorization
- `[ ]` Enforce strict naming convention constraints across all components.
- `[ ]` Enforce naming conventions for test classes and methods.
- `[ ]` Add naming and return-type constraints for Spring Data JPA Repository methods.
- `[ ]` Enforce general Java coding style constraints.
- `[ ]` Use parameterized tests for repetitive test scenarios.
- `[ ]` Implement test case categorization for different environments (e.g., unit, integration, end-to-end).
- `[ ]` Implement test case prioritization based on business impact.

### Day 5: CI/CD Orchestration & Telemetry
- `[ ]` Automate test execution and reporting using CI/CD pipelines.
- `[ ]` Implement test coverage checks for critical components.
- `[ ]` Integrate with code review tools for automated feedback.
- `[ ]` Implement automated test result analysis and alerting.

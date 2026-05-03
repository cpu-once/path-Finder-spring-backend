# GEMINI.md - Project Context & Instructions

This file serves as the foundational mandate for all AI interactions within the `pathFinder-spring-backend` project. It provides the necessary context on architecture, tech stack, and development standards.

## Project Overview
**PathFinder Spring Backend** is an AI-driven PMP (Project Management Professional) framework and BIM (Building Information Modeling) work graph query system. It focuses on parsing IFC data, modeling construction work as a directed acyclic graph (DAG), and providing an LLM-integrated interface for querying project status, tracking decisions, and performing impact analysis based on established PMP principles.

### Core Tech Stack
- **Runtime:** Java 25 (utilizing compact headers, structured concurrency, etc.)
- **Framework:** Spring Boot 4.0.5, Spring Modulith 2.0.5
- **Build System:** Gradle (multi-project with typesafe accessors and version catalogs)
- **Database:** Multi-datasource H2 (one instance per module) -> Migrating to Single DB with Schema Separation
- **Quality & Testing:** JUnit 5, ArchUnit 1.3.0, Spring REST Docs, JaCoCo, DataFaker
- **Key Libraries:** Lombok, Spring AI (planned), IfcOpenShell (planned)

## Architecture & Design Patterns

### Spring Modulith Structure
The application follows a strict modular monolith architecture with five primary business modules. Boundaries and dependencies are enforced via `package-info.java` and ArchUnit tests.

**Module Dependency Pipeline:**
`baseline` → `field` → `progress` → `valuation` → `insights`

- **Communication:** Cross-module communication against the pipeline flow MUST use **Events** (`@ApplicationModuleListener`) to maintain loose coupling.
- **Persistence:** Migrating to a single `DataSource` with logical schema separation per module.
- **Constants:** Use `<Module>PersistenceConstants` for bean names and property prefixes to avoid hardcoding strings.

### Internal Layering (Per Module)
Each module must adhere to the following layer structure:
1. `controller`/`api`: Web entry points. May only depend on `service`.
2. `service`: Business logic. May not depend on `controller`.
3. `repository`: Data access. May only depend on `entity`.
4. `entity`/`dto`: Data models. Leaf packages.
5. `infrastructure`: Module-specific configuration.
6. `event`/`listener`: Cross-module communication components.

### Pure Math Core
`core/core-math` is a pure Java library (no Spring dependencies). It is reserved for algorithmic helpers usable by any module.

## Development Workflows

### Build & Run Commands
Always use the Gradle wrapper (`./gradlew`).
- **Full Build:** `./gradlew build`
- **Run App:** `./gradlew :apps:path-finder-app:bootRun`
- **Test App:** `./gradlew :apps:path-finder-app:test`
- **Architecture Test:** `./gradlew :apps:path-finder-app:test --tests "com.coralstay.pathfinderspringbackend.architecture.*"`
- **Generate Docs:** `./gradlew :apps:path-finder-app:asciidoctor`

### Coding Standards
- **Indentation:** 2 spaces.
- **Line Length:** Max 100 characters.
- **Naming Conventions:**
    - Configurations: `*Config`
    - Services: `*Service`
    - Repositories: `*Repository` (interfaces)
    - Tests: `*Test` or `*Tests`
- **Language:** Korean is permitted and common in comments, `@DisplayName`, and commit messages.

### Git Conventions
- **Prefixes:** `feat`, `fix`, `refactor`, `chore`, `docs`, `test`.
- **Strategy:** "Elephant Commits" (micro-commits) during development, followed by "Squash and Merge" for PRs to keep `main` history clean.

## Important Reference Files
- `CLAUDE.md`: Comprehensive technical guide and command reference.
- `docs/back-log/backlog.md`: The single source of truth for project status and pending tasks.
- `gradle/libs.versions.toml`: Centralized dependency version management.
- `docs/guidelines/git-convention.md`: Detailed Git workflow rules.

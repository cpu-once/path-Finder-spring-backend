# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Test Commands

Gradle multi-project build (Java 25, Spring Boot 4.0.5). Always use the wrapper.

```bash
./gradlew build                                  # full build of all subprojects
./gradlew :apps:path-finder-app:bootRun          # run the Spring Boot app
./gradlew :apps:path-finder-app:test             # run all tests in the app module
./gradlew :core:math:test                        # run core-math tests
./gradlew :apps:path-finder-app:test --tests "com.coralstay.pathfinderspringbackend.architecture.ModulithVerificationTest"
./gradlew :apps:path-finder-app:test --tests "*PersistenceConfigTest"
./gradlew :apps:path-finder-app:asciidoctor      # generate REST Docs (depends on test)
```

Project paths are mapped via `settings.gradle.kts` — Gradle module `:apps:path-finder-app` lives at `apps/pathfinder-app/`, and `:core:math` lives at `core/core-math/`. Internal module deps use typesafe accessors (`projects.core.math`). Versions are managed in `gradle/libs.versions.toml`.

H2 console is enabled at runtime; per-module H2 files land under `apps/pathfinder-app/build/db/`.

## Architecture

### Spring Modulith with five business modules

The app is a **Spring Modulith** application. Module boundaries are declared in each module's `package-info.java` via `@ApplicationModule(allowedDependencies = {...})`. The dependency graph is a strict unidirectional pipeline:

```
baseline → field → progress → valuation → insights
```

(`baseline` depends on nothing; `insights` may depend on all four upstream modules.) Cross-module communication that goes against the natural flow uses **events** — see `baseline/event/BaselineCreatedEvent` published by `BaselineEventPublisher`, consumed in `progress/listener/BaselineEventListener` via `@ApplicationModuleListener` (runs after the publisher's transaction commits, in a separate transaction).

When adding a new cross-module integration, prefer events over direct service calls, and update `package-info.java` only if a direct dependency is genuinely required. The pipeline ordering and the modulith rules are both enforced by tests:

- `architecture/ModulithVerificationTest` — Spring Modulith's own `modules.verify()` plus a count check (must remain 5).
- `architecture/ModuleDependencyTest` — ArchUnit layered architecture using the pipeline rule above.
- `architecture/DocumentationTest` — generates PlantUML/canvas docs into the build dir.
- `<module>/architecture/<Module>ArchitectureTest` — per-module reverse-dependency guards.

### Multi-datasource: one H2 DB per module

Each module owns an independent H2 database, `DataSource`, `EntityManagerFactory`, and `TransactionManager`. Spring Boot's `DataSourceAutoConfiguration` and `HibernateJpaAutoConfiguration` are **explicitly excluded** in `PathFinderSpringBackendApplication` because every module wires its own persistence config under `<module>/infrastructure/<Module>PersistenceConfig.java`.

Important rules when touching persistence:

- All bean names, package paths, and `app.datasource.<module>` property prefixes are pulled from `<Module>PersistenceConstants` — keep these constants as the single source of truth, never hardcode the strings (the ArchUnit/test setup grew out of a backlog item to eliminate hardcoded values).
- The **baseline** module's beans are `@Primary`. If you change which module is primary, `PersistenceConfigTest.baselineIsPrimary` and downstream wiring will need to change together.
- Each `@EnableJpaRepositories` is scoped to that module's package. A repository placed in the wrong package will silently bind to the wrong EM.
- New module = new datasource block in `application.yaml` + new `*PersistenceConfig` + new `*PersistenceConstants` + update the `MODULES` array in `PersistenceConfigTest` and the count assertion in `ModulithVerificationTest`.

### Intra-module layer layout

Inside each module, packages follow a fixed shape. ArchUnit (`architecture/LayerStructureTest`) enforces these rules with `noClasses().that().resideInAPackage(...)`:

- `controller` / `api` → may depend only on `service` (no direct repository, entity, or infrastructure access).
- `service` → may not depend on `controller`.
- `repository` → may depend on `entity` only (no infrastructure, simulation, dto, controller, service).
- `entity` and `dto` → leaf packages, must not depend on other layers in the same module.
- `simulation` → must not depend on `infrastructure`.
- `infrastructure` → persistence config + constants; not for business logic.
- `event` / `listener` → cross-module event types and `@ApplicationModuleListener` consumers.

Naming rules enforced by `architecture/NamingConventionTest`: `@Configuration` ends with `Config`, `@Service` ends with `Service`, repository interfaces end with `Repository`, `DataSeeder` implementations end with `DataSeeder`, no `I`-prefixed interfaces. Test classes must end with `Test` or `Tests`. `Repository`/`Entity` classes should be package-private (or protected) — see `architecture/EncapsulationTest`.

### core/core-math

Pure-Java library (no Spring), JUnit 5 only. Reserved for math/algorithmic helpers usable from any module. Do **not** add Spring dependencies here.

## Conventions

- **Java 25** toolchain (set per-subproject). Some Java 25 features (Compact Object Headers, Scoped Values, Structured Concurrency, Generational Shenandoah GC) are part of the project rationale — see `docs/guidelines/global/java-version.md`.
- **Lombok** is available in `apps/pathfinder-app` (compile-only + annotation processor, also for tests).
- **Editor**: 2-space indent, LF, UTF-8, max Java line length 100 (`.editorconfig`). IntelliJ Google Java Style XML is in `docs/coding-style/`.
- **Korean** is used freely in code comments, `@DisplayName`, and docs. Don't translate existing Korean text when editing nearby code.
- **Commits**: prefix with `feat` / `fix` / `refactor` / `chore` / `docs` / `test` (see `docs/guidelines/git-convention.md`). Korean commit subjects are accepted and common in `git log`.

## Where to look

- `docs/guidelines/Requierments.md` — high-level product intent (BIM-centric domain sandbox with LLM-driven Q&A; DataFaker for synthetic data). Module status: `plan` in progress, others unimplemented.
- `docs/back-log/backlog.md` — single source of pending work items (TODOs and architectural test backlog). Update this when checking off items.
- `docs/guidelines/global/` — ADR-style notes on Java/Spring choices (most files are still empty stubs).
- `docs/guidelines/<module>/{controller,entity,repository,service,module-global}/` — per-module guideline directories (currently empty placeholders).

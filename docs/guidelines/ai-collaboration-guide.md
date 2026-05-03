# AI-Assisted Development Collaboration Guide (Claude Code & Gemini CLI)

This document outlines the key points, potential pitfalls, and essential human-learning requirements when developing the `PathFinder Spring Backend` project using AI CLI tools (Claude Code, Gemini CLI).

This project uses **Spring Modulith**, **Multi-module Gradle**, and **Event-Driven Architecture (EDA)**. These are relatively advanced concepts that AI can sometimes misunderstand or implement sub-optimally without strict guidance.

---

## 1. Known Problems & Challenges with AI CLIs

### 1.1 Context Window & Hallucination in Multi-Module Projects
- **Problem**: AI CLIs often struggle to maintain context across a multi-module Gradle project (`apps/pathfinder-app`, `core/core-math`). They might try to add dependencies to the wrong `build.gradle.kts` or misinterpret the boundaries between the application module and the pure Java library.
- **Mitigation**: ALWAYS specify the exact path of the `build.gradle.kts` file you want the AI to modify. (e.g., "Modify `apps/pathfinder-app/build.gradle.kts` to add...").

### 1.2 Breaking Spring Modulith Constraints
- **Problem**: Spring Modulith relies heavily on `package-info.java` to define boundaries and `@ApplicationModuleListener` for communication. AI might try to "helpfully" inject a `Service` from the `field` module directly into the `progress` module's Controller, which violates our `baseline -> field -> progress -> valuation -> insights` pipeline constraint.
- **Mitigation**: Remind the AI to strictly adhere to Event-Driven Architecture (EDA). If the AI suggests a direct dependency between modules, reject it and ask for an event-based solution.

### 1.3 Testing Isolation & ArchUnit Overwrites
- **Problem**: AI might write standard `@SpringBootTest` classes that spin up the entire application context, making tests slow. Furthermore, it might not understand our strict ArchUnit rules (e.g., "DTOs must end with 'Dto'").
- **Mitigation**: Force AI to use slice tests (`@WebMvcTest`, `@DataJpaTest`) whenever possible. Frequently run the ArchUnit tests (`./gradlew test --tests "*architecture*"`) to catch AI mistakes early.

### 1.4 Shell Command Dangers (Destructive Edits)
- **Problem**: AI tools might attempt to use `sed`, `awk`, or `>` to modify files directly in the shell. This can corrupt files or desync the IDE.
- **Mitigation**: Enforce the rule (as stated in System Prompts) that AI MUST use specialized `write_file` tools rather than raw shell commands for file modifications.

---

## 2. Essential Human Learning (What the Developer MUST Understand)

To successfully guide the AI and review its output, the human developer must thoroughly understand the following concepts:

### 2.1 Spring Modulith Core Concepts
- **Learning Requirement**: You must understand how `@ApplicationModule` works, how to define allowed dependencies in `package-info.java`, and how the Event Publication Registry works (especially for Fallback/DLQ scenarios).
- **Why**: AI will inevitably try to bypass these boundaries for convenience. You are the architect who must enforce the rules.

### 2.2 Event-Driven Architecture (EDA) & Transactional Outbox Pattern
- **Learning Requirement**: Understand `@TransactionalEventListener` and Spring Modulith's built-in Event Publication Registry.
- **Why**: When Epic 3 (Universal WAL) is implemented, events will be the backbone of the system. You must know how events guarantee consistency between the business database and the WAL.

### 2.3 Write-Ahead Logging (WAL) vs. Traditional CRUD
- **Learning Requirement**: Understand the concept of "Append-Only" logs and Immutable Ledgers.
- **Why**: AI is trained heavily on standard CRUD (Create, Read, Update, Delete) patterns. You will have to explicitly stop the AI from generating `update()` or `delete()` methods in Repositories related to the Valuation Ledger and Decision Records.

### 2.4 Multi-Datasource & Schema Separation (H2/PostgreSQL)
- **Learning Requirement**: Understand how to configure a single `DataSource` but enforce logical separation using JPA `@Table(schema="module_name")` and Flyway migration schemas.
- **Why**: The AI might get confused between physical separate databases (multiple connection URLs) and logical schemas (single connection, separated tables).

### 2.5 Prompt Engineering for Incremental Delivery
- **Learning Requirement**: Learn to slice tasks from the `backlog.md` into highly specific, bite-sized prompts.
- **Why**: Asking AI to "Implement Epic 2" will result in chaotic, broken code. You must ask for exactly one Task at a time (e.g., "Create the `WorkGroup` Entity with schema 'baseline'").

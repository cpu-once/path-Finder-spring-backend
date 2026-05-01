# Project Backlog (Step-by-Step Task Breakdown)

## Epic 1. High Priority: Infrastructure & Architecture Setup

### [Story 1.1] Database & Schema Integration
- `[ ]` [Task] `application.yaml`의 5개 다중 데이터소스 설정을 단일 데이터소스(e.g., PostgreSQL/H2)로 통합
- `[ ]` [Task] 통합된 단일 DB 연결을 사용하는 공통 `DataSource` 빈(Bean) 생성
- `[ ]` [Task] 각 모듈의 `PersistenceConfig` 파일에서 다중 DB 설정 제거 및 공통 `DataSource` 주입받도록 수정
- `[ ]` [Task] 각 모듈 Entity 패키지에 `@Table(schema = "모듈명")` 지정하여 물리적 DB 대신 논리적 스키마(Schema)로 데이터 분리 강제

### [Story 1.2] Database Migration (Flyway/Liquibase)
- `[ ]` [Task] `build.gradle.kts`에 Flyway(또는 Liquibase) 의존성 추가
- `[ ]` [Task] 각 모듈 스키마(baseline, field 등) 생성을 위한 V1 초기 마이그레이션 스크립트 작성
- `[ ]` [Task] Spring Boot 구동 시 Flyway가 각 모듈 스키마별로 마이그레이션을 실행하도록 환경 설정

### [Story 1.3] Spring Security & Auth (JWT/OAuth2)
- `[ ]` [Task] `build.gradle.kts`에 `spring-boot-starter-security`, `jjwt` 의존성 추가
- `[ ]` [Task] `SecurityConfig.java` 생성 및 세션 상태 Stateless(무상태)로 설정
- `[ ]` [Task] 로그인/회원가입 처리용 AuthController 및 AuthService 뼈대 작성
- `[ ]` [Task] JWT 발급(Access/Refresh) 및 서명 검증 유틸리티 클래스 구현
- `[ ]` [Task] HTTP Request에서 JWT를 추출하고 검증하는 커스텀 `JwtAuthenticationFilter` 구현 및 Security 필터 체인에 등록
- `[ ]` [Task] 사용자 권한(Role)별 API 접근 제어 규칙 설정

### [Story 1.4] Architectural Constraints Enforcement (ArchUnit)
- `[ ]` [Task] Spring Boot Starter BOM 버전을 파싱하여 모든 의존성이 동일한 BOM 버전을 따르는지 검증하는 ArchUnit 테스트 작성
- `[ ]` [Task] DTO 클래스는 `*Dto` 또는 `*Response`, `*Request`로 끝나야 한다는 ArchUnit 규칙 추가
- `[ ]` [Task] Controller는 `*Controller` 또는 `*Api`로 끝나야 한다는 ArchUnit 규칙 추가
- `[ ]` [Task] Domain Object(Entity, VO)는 Jpa 관련 어노테이션이나 Core 어노테이션 외의 Web/Service 레이어 의존성을 가지지 못하게 제한
- `[x]` [Task] Define layer-specific structural constraints. (Applies to: DTO, Domain Object, Service, Repository, Controller) *(Completed via `LayerStructureTest`)*
- `[x]` [Task] Restrict dependencies between Controller -> Service -> Repository -> Domain layers within each package. *(Completed via `LayerStructureTest` / `EncapsulationTest`)*
- `[x]` [Task] Refactor all hardcoded string values to be programmable/dynamic constants. *(Completed via `PersistenceConstants` & DisplayName refactoring)*

---

## Epic 2. Medium Priority: Core Feature Implementation (Data Seeding)
- `[ ]` [Story] `BaselineDataSeeder`: Datafaker 객체를 활용해 Project, Task 등 초기 기본 설정 데이터 100건 생성 및 DB insert 로직 구현
- `[ ]` [Story] `FieldDataSeeder`: 생성된 Baseline 데이터를 바탕으로 가상의 작업자, 건설장비, 작업 일지 데이터 무작위 생성 로직 구현
- `[ ]` [Story] `ProgressDataSeeder`: Field 데이터를 기반으로 공정률(Progress Rate), 지연 상태(Delay) 등을 계산한 임시 데이터 생성
- `[ ]` [Story] `ValuationDataSeeder`: Progress 데이터를 기반으로 기성고(Valuation), 비용 청구 등의 재무 데이터 생성
- `[ ]` [Story] `InsightsDataSeeder`: 앞선 모든 데이터를 집계하여 대시보드 통계용 Dummy 데이터 생성
- `[ ]` [Task] 위 5개의 Seeder가 올바른 순서(Baseline -> Field -> Progress -> Valuation -> Insights)로 실행되도록 ApplicationRunner 등록

---

## Epic 3. Medium Priority: Performance & Scalability

### [Story 3.1] Redis Integration & Global Caching
- `[ ]` [Task] `build.gradle.kts`에 `spring-boot-starter-data-redis` 의존성 추가
- `[ ]` [Task] `RedisConfig.java` 작성 (RedisConnectionFactory, RedisTemplate 빈 등록 및 직렬화 설정)
- `[ ]` [Task] `CacheConfig.java` 작성 (RedisCacheManager 세팅 및 TTL 설정)
- `[ ]` [Task] JWT Refresh Token을 Redis에 저장/조회하는 Repository 로직 구현
- `[ ]` [Task] Insights 모듈 등 조회 빈도가 높은 API 서비스 메서드에 `@Cacheable`, `@CacheEvict` 어노테이션 적용

### [Story 3.2] Spring Batch Setup
- `[ ]` [Task] 루트 디렉터리에 `apps/pathfinder-batch` 신규 멀티 모듈 디렉토리 생성 및 `build.gradle.kts` 설정
- `[ ]` [Task] 루트 `settings.gradle.kts`에 `:apps:pathfinder-batch` 등록
- `[ ]` [Task] Batch 전용 애플리케이션 진입점(`@EnableBatchProcessing`) 클래스 작성
- `[ ]` [Task] 일일 공정률(Progress) 정산 및 기성고(Valuation) 집계를 위한 첫 번째 Batch Job 및 Step(ItemReader, Processor, Writer) 구현

---

## Epic 4. Medium Priority: Test Infrastructure & Refactoring
- `[ ]` [Task] 각 테스트 클래스마다 DB 상태를 롤백시키기 위한 `@Transactional` 또는 커스텀 `@AfterEach` truncate 쿼리 로직 작성 (Ensure test isolation)
- `[ ]` [Task] 테스트 환경 구동 속도 최적화를 위해 `@SpringBootTest` 대신 슬라이스 테스트(`@DataJpaTest`, `@WebMvcTest`)로 전환 가능한 클래스들 식별 및 리팩토링
- `[ ]` [Task] `TestEntityManager` 또는 전용 `Fixture` 클래스를 활용하여 반복되는 테스트 데이터 Setup/Teardown 코드를 공통 유틸리티로 추출
- `[ ]` [Task] 비즈니스 로직 경계값(Boundary) 테스트를 위해 `@ParameterizedTest`와 `@CsvSource`/`@MethodSource`를 적용하여 반복 테스트 케이스 통합
- `[ ]` [Task] 하드코딩된 `LocalDateTime.now()` 등을 `java.time.Clock` 빈으로 교체하여 테스트에서 시간을 Mocking할 수 있도록 제약(ArchUnit) 및 리팩토링
- `[x]` [Task] Split file into multiple test classes for better organization and maintainability. *(Completed via `architecture/*Test` split)*

---

## Epic 5. Low Priority: Code Quality & Conventions Enforcement

### [Story 5.1] Linting & Formatting
- `[ ]` [Task] `build.gradle.kts`에 `com.diffplug.spotless` 플러그인 추가
- `[ ]` [Task] Spotless 포맷팅 규칙(Google Java Format 등) 적용 및 `./gradlew spotlessApply` 파이프라인 구성
- `[ ]` [Task] 팀원 간 동일한 IDE 검사 기준을 위해 SonarLint 플러그인 룰셋(Rule Set) 파일(`sonar-project.properties`) 루트 경로에 작성

### [Story 5.2] SonarQube & JaCoCo (Test Coverage)
- `[ ]` [Task] `build.gradle.kts`에 `jacoco` 플러그인 추가
- `[ ]` [Task] JaCoCo 테스트 리포트 생성 태스크 및 커버리지 최소 기준(Threshold, 예: Line 80%, Branch 70%) 설정
- `[ ]` [Task] SonarQube Scanner 플러그인 연동 및 `sonar.host.url`, `sonar.login` 환경변수 세팅 스크립트 작성

### [Story 5.3] Coding Style Enforcement (ArchUnit)
- `[x]` [Task] Enforce strict naming convention constraints across all components. *(Completed via `NamingConventionTest`)*
- `[x]` [Task] Enforce naming conventions for test classes and methods. *(Completed via `NamingConventionTest`)*
- `[x]` [Task] Enforce general Java coding style constraints. *(Completed via `NamingConventionTest`: no public fields, final utility classes)*
- `[ ]` [Task] Add naming and return-type constraints for Spring Data JPA Repository methods.
- `[ ]` [Task] Restrict the usage of specific external library functions.
- `[ ]` [Task] Extract and apply useful advanced rules from the official ArchUnit documentation.

---

## Epic 6. Low Priority: CI/CD, Telemetry & Security

### [Story 6.1] CI/CD Pipeline
- `[ ]` [Task] `.github/workflows/ci.yml` 생성 및 Java 버전에 맞는 Build & Test 자동화 Action 스크립트 작성
- `[ ]` [Task] CI 파이프라인 내에 Spotless 검사, JaCoCo 리포트 업로드, SonarQube 분석 단계 추가
- `[ ]` [Task] 성공적인 CI 빌드 후 Slack/Discord 등으로 테스트 결과를 전송하는 알림(Alerting) 스크립트 연동

### [Story 6.2] Error Handling & Logging
- `[ ]` [Task] 전역 예외 처리를 위한 `@RestControllerAdvice` 클래스 생성
- `[ ]` [Task] Spring 3+ 표준인 ProblemDetail(RFC 7807) 객체를 반환하도록 커스텀 Exception Handler 메서드 구현
- `[ ]` [Task] `src/main/resources/logback-spring.xml` 생성 및 콘솔/파일 로깅 패턴 포맷 표준화
- `[ ]` [Task] API 요청마다 고유한 Trace ID(traceId, spanId)를 부여하도록 MDC(Mapped Diagnostic Context) 필터 구현

### [Story 6.3] Telemetry & Distributed Tracing
- `[ ]` [Task] `micrometer-tracing-bridge-brave` 및 `zipkin-reporter-brave` 의존성 추가
- `[ ]` [Task] `application.yaml`에 Zipkin 서버 연동 설정 및 샘플링 확률(Sampling Rate) 지정
- `[ ]` [Task] Prometheus 메트릭 수집을 위해 `spring-boot-starter-actuator` 추가 및 `/actuator/prometheus` 엔드포인트 오픈

### [Story 6.4] Modulith Event Fallback & Security
- `[x]` [Task] Spring Modulith 이벤트를 활용한 모듈 간 통신(EDA) 보완 및 장애 격리(Fallback/DLQ) 로직 *(Partially Completed via `BaselineEventPublisher` & `BaselineEventListener`)*
- `[ ]` [Task] `.github/PULL_REQUEST_TEMPLATE.md` 및 `ISSUE_TEMPLATE.md` 템플릿 파일 생성
- `[ ]` [Task] CI 파이프라인에 Mythos 또는 깃허브 Dependabot/CodeQL 연동하여 보안 취약점 스캔(SAST) 단계 추가

---

## Epic 7. Core Domain: IFC & Valuation Engine (New)

### [Story 7.1] IFC 4.3 Data Parsing & Model Mapping
- `[ ]` [Task] IFC 파싱을 위한 Java 라이브러리(IfcOpenShell 등) 리서치 및 `build.gradle.kts` 추가
- `[ ]` [Task] `IfcWall`, `IfcSlab` 등 주요 건축 요소를 파싱하여 내부 POJO(AppWall, AppSlab 등)로 변환하는 파서 클래스 작성
- `[ ]` [Task] 변환된 체적(Volume), 길이(Length), 면적(Area) 데이터를 DB에 저장하는 Repository 및 Entity 매핑 로직 구현

### [Story 7.2] Valuation Mapping & Computation
- `[ ]` [Task] 표준품셈/일위대가 단가 데이터를 로드하여 `valuation` 모듈 DB에 저장 (엑셀/CSV 파서 등 활용)
- `[ ]` [Task] `baseline` 모듈의 IFC 객체(POJO)와 `valuation` 모듈의 일위대가 코드를 매핑하는 룰 엔진(Rule Engine) 초안 작성
- `[ ]` [Task] (예: 체적 * 단위당 단가) 비용 산출 로직을 담당하는 핵심 Valuation Service 구현
- `[ ]` [Task] 산출된 비용 내역을 바탕으로 총 프로젝트 예상 비용(Estimate Cost)을 반환하는 REST API 컨트롤러 구현

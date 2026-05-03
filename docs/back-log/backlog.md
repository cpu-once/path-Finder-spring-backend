# Project Backlog (Step-by-Step Task Breakdown)

## Epic 1. Absolute Priority: Real-time Developer Experience (DevEx) & Continuous Profiling

### [Story 1.1] Real-time Quality & Performance Feedback Tools

**🎯 Goal: 테스트 커버리지 자동화 (JaCoCo)**
- `[ ]` [Task] `gradle/libs.versions.toml`에 `jacoco` 플러그인 버전 정의 (예: 0.8.11)
- `[ ]` [Task] 루트 `build.gradle.kts` (또는 `subprojects`)에 `jacoco` 플러그인 추가 (멀티 모듈 리포팅 취합 설정 포함)
- `[ ]` [Task] 하위 모듈(`pathfinder-app`, `core-math`)의 `build.gradle.kts`에 `jacocoTestReport` task 설정 (XML/HTML 리포트 활성화)
- `[ ]` [Task] `test` task 완료 후 `jacocoTestReport` 자동 실행되도록 설정 (`finalizedBy`)
- `[ ]` [Task] 하위 모듈의 `build.gradle.kts`에 `jacocoTestCoverageVerification` task 설정 추가
- `[ ]` [Task] `jacocoTestCoverageVerification` rule에서 INSTRUCTION, BRANCH 커버리지 minimum 1.0 (100%) 설정
- `[ ]` [Task] `jacocoTestCoverageVerification`에서 DTO, Entity 등 테스트 제외 패키지(excludes) 설정
- `[ ]` [Task] `check` task 수행 시 `jacocoTestCoverageVerification`이 실행되도록 의존성 설정

**🎯 Goal: 정적 분석 환경 구성 (SonarLint & SonarQube)**
- `[ ]` [Task] `README.md` (또는 개발가이드)에 IntelliJ SonarLint 플러그인 설치 및 공통 Rule Set 연동 방법 문서화
- `[ ]` [Task] 프로젝트 루트에 SonarLint 공통 룰셋(rule set) 설정 파일 생성 및 커밋
- `[ ]` [Task] `docker-compose.yml` (로컬 개발용)에 SonarQube 서버 컨테이너 설정 추가
- `[ ]` [Task] `build.gradle.kts`에 `org.sonarqube` 플러그인 추가
- `[ ]` [Task] `build.gradle.kts`에 `sonar` property 설정 (host.url, projectKey 등)
- `[ ]` [Task] 로컬 SonarQube 서버 실행 후 Quality Gate 최소 커버리지 조건 100% 설정 스크립트 작성

**🎯 Goal: 실시간 프로파일링 및 부하 테스트 환경**
- `[ ]` [Task] JVM 옵션으로 JFR(Java Flight Recorder) 활성화 스크립트(또는 IntelliJ Run Configuration 템플릿) 작성
- `[ ]` [Task] JFR 기록을 덤프(dump)하는 로컬 개발용 쉘 스크립트(JCMD 활용) 작성
- `[ ]` [Task] Async-profiler 다운로드 및 로컬 실행을 위한 쉘 스크립트 작성 (CPU/Allocation 프로파일링)
- `[ ]` [Task] Apache JMeter용 기본 부하 테스트 JMX 스크립트 템플릿(단순 API 호출) 작성
- `[ ]` [Task] Spring Boot 3.2+ 의 `spring-boot-devtools`를 `build.gradle.kts`에 추가하여 클래스 리로딩(Hot-Swap) 설정

### [Story 1.2] Multi-AI Collaboration Workflow

**🎯 Goal: AI 에이전트 동시성 제어 및 쉘 제어 훅**
- `[ ]` [Task] 다중 AI 에이전트 동시 사용 시 컨텍스트 충돌 방지를 위한 디렉토리/모듈 단위 `.agent-lock` 파일 생성 및 검증 쉘 스크립트 작성
- `[ ]` [Task] AI가 터미널 명령어 실행 전 구문을 검증(ShellCheck 호출 등)하는 프롬프팅 훅(Hook) 시스템 가이드라인 문서 작성

### [Story 1.3] Advanced Runtime Observability & Control

**🎯 Goal: 런타임 제어 및 외부 API 장애 대비**
- `[ ]` [Task] `-XX:+HeapDumpOnOutOfMemoryError` 등 JVM 옵션을 추가하여 OOM 발생 시 자동 힙 덤프 설정
- `[ ]` [Task] `build.gradle.kts`에 Spring Boot Actuator 의존성 추가
- `[ ]` [Task] `application.yml`에 `/actuator/loggers` 엔드포인트 활성화 설정 추가 (실시간 로그 레벨 변경용)
- `[ ]` [Task] AI API(Gemini 등) 호출 실패(Quota 초과 등 429 에러) 시 재시도 간격을 백오프(Backoff) 방식으로 조절하는 커스텀 `RetryTemplate` 또는 Resilience4j 로직 구현

---

## Epic 2. Highest Priority: Core Domain (AI-Driven BIM Work Graph Query System)

### [Story 2.1] Database Integration & Docker Local Environment (Highest)

**🎯 Goal: 단일 DataSource(H2/PostgreSQL) 통합 및 로컬 도커 환경 구축**
- `[ ]` [Task] `application.yml`에서 기존 5개 모듈별 DB 설정 모두 제거
- `[ ]` [Task] `application.yml`에 통합 단일 DB(H2 또는 PostgreSQL) 접속 정보(`spring.datasource.*`) 추가
- `[ ]` [Task] 각 모듈(`baseline`, `field`, `progress`, `valuation`, `insights`)의 `infrastructure` 내 개별 DataSource/TransactionManager 등록 빈(Bean) 일괄 제거
- `[ ]` [Task] 단일 `DataSource`를 사용하는 전역 `JpaConfig` 클래스 생성 (Spring Boot AutoConfiguration 활용 시 생략 가능 여부 확인)
- `[ ]` [Task] 로컬 개발용 단일 DB 컨테이너 실행을 위한 `docker-compose.yml` 작성
- `[ ]` [Task] `build.gradle.kts`에 `spring-boot-docker-compose` 의존성 추가 (앱 실행 시 도커 자동 기동)

### [Story 2.2] Database Migration (Flyway/Liquibase)

**🎯 Goal: Flyway 마이그레이션 적용 및 스키마 초기화**
- `[ ]` [Task] `gradle/libs.versions.toml`에 `flyway-core` 의존성 정의 및 `build.gradle.kts` 추가
- `[ ]` [Task] `application.yml`에 `spring.flyway.enabled=true` 및 기본 설정 추가
- `[ ]` [Task] 각 모듈의 Entity 클래스 상단 `@Table(schema = "모듈명")` 어노테이션 일괄 추가하여 논리적 분리 보장
- `[ ]` [Task] `src/main/resources/db/migration` 하위에 `V1__init_baseline_schema.sql` 작성 (Project, Task 테이블 생성 DDL)
- `[ ]` [Task] `src/main/resources/db/migration` 하위에 `V2__init_field_schema.sql` 작성 (Worker, Equipment 테이블 생성 DDL)
- `[ ]` [Task] `src/main/resources/db/migration` 하위에 `V3__init_progress_schema.sql` 작성 (Progress 테이블 생성 DDL)

### [Story 2.3] API Documentation & Error Handling Foundation

**🎯 Goal: REST Docs와 Swagger의 결합 (restdocs-api-spec)**
- `[ ]` [Task] `build.gradle.kts`에 `com.epages.restdocs-api-spec` 플러그인 및 의존성 추가
- `[ ]` [Task] `build.gradle.kts`에 `openapi3` Task를 설정하여, REST Docs 테스트 성공 시 OpenAPI 3.0 스펙(JSON) 자동 생성 파이프라인 구축
- `[ ]` [Task] `build.gradle.kts`에 `springdoc-openapi-starter-webmvc-ui` (Swagger UI) 의존성 추가
- `[ ]` [Task] 생성된 OpenAPI 스펙 파일(JSON)을 Swagger UI 경로로 서빙할 수 있도록 `application.yml` 설정 및 커스텀 WebMvc Config 작성
- `[ ]` [Task] 컨트롤러 테스트 작성 시, `MockMvcRestDocumentation` 대신 `MockMvcRestDocumentationWrapper`를 사용하도록 팀 가이드라인 주석 작성

**🎯 Goal: 전역 예외 처리(ProblemDetail) 뼈대**
- `[ ]` [Task] 전역 예외 처리를 위한 `GlobalExceptionHandler` 클래스 생성 및 `@RestControllerAdvice` 부착
- `[ ]` [Task] 커스텀 `BusinessException` 및 `MethodArgumentNotValidException`을 캐치하여 Spring 3 `ProblemDetail` 객체로 변환 반환하는 메서드 작성

### [Story 2.4] IFC Data Ingestion & Graph Modeling

**🎯 Goal: IFC 파싱 라이브러리 연동**
- `[ ]` [Task] `gradle/libs.versions.toml`에 IFC 파싱 라이브러리(IfcOpenShell-Java 등) 버전 정의
- `[ ]` [Task] `build.gradle.kts`의 dependencies에 IFC 파싱 라이브러리 추가

**🎯 Goal: 파싱 서비스 및 업로드 API 구현**
- `[ ]` [Task] IFC 파싱을 수행하는 `IfcParserService` 인터페이스 생성
- `[ ]` [Task] `IfcParserService` 구현체 클래스 생성 및 `@Service` 등록
- `[ ]` [Task] 파일 업로드(MultipartFile)로 IFC 파일을 받아 파싱 서비스에 전달하는 `IfcUploadController` 생성

**🎯 Goal: 그래프 데이터 엔티티(WorkGroup/WorkDependency) 모델링**
- `[ ]` [Task] 공종(Trade) 또는 작업 단위(Task)를 의미하는 `WorkGroup` 엔티티 클래스 생성 및 JPA 어노테이션 추가
- `[ ]` [Task] `WorkGroup` 엔티티 내에 소속된 IFC 객체 ID 목록을 저장하는 `List<String> elements` 필드 추가
- `[ ]` [Task] IFC 파싱 결과를 바탕으로 `WorkGroup` 엔티티를 생성하고 DB에 저장하는 `WorkGroupRepository` 로직 작성
- `[ ]` [Task] `WorkGroup` 간 선행/후행 관계를 나타내는 `WorkDependency` 엔티티 생성 (선행 ID, 후행 ID 포함)
- `[ ]` [Task] `WorkDependency` 엔티티 리스트를 기반으로 메모리상에 DAG(Directed Acyclic Graph)를 구성하는 `WorkGraph` 자료구조 클래스 구현
- `[ ]` [Task] `WorkGraph` 객체 생성 시 순환 참조(Cycle) 발생 여부를 검증하는 로직 추가

### [Story 2.5] LLM Context Preparation & Serialization

**🎯 Goal: 그래프 모델의 AI 프롬프트용 직렬화**
- `[ ]` [Task] AI에게 전달할 경량화된 `WorkGroupDto`, `WorkDependencyDto` 생성
- `[ ]` [Task] `WorkGraph` 객체를 JSON 문자열로 변환하는 `GraphSerializer` 컴포넌트(Jackson ObjectMapper 활용) 구현
- `[ ]` [Task] AI가 이해하기 쉬운 텍스트 기반 Markdown 형태로 그래프 구조를 직렬화하는 포맷터 메서드 구현
- `[ ]` [Task] 직렬화된 텍스트의 Token 길이를 계산하는 유틸리티(예: JTokkit 사용) 구현 및 제한 초과 시 예외 처리 로직 추가

**🎯 Goal: 프롬프트 템플릿 및 상수 설계**
- `[ ]` [Task] AI에게 역할을 부여하는 System Prompt 텍스트 상수 클래스(`PromptConstants`) 생성
- `[ ]` [Task] 사용자 질의와 직렬화된 그래프 컨텍스트 문자열을 결합하는 프롬프트 템플릿(String 템플릿 또는 Spring AI `PromptTemplate`) 로직 구현

### [Story 2.6] AI Query Integration (Spring AI / LangChain)

**🎯 Goal: Spring AI 연동 설정**
- `[ ]` [Task] `gradle/libs.versions.toml`에 `spring-ai-openai-spring-boot-starter` (또는 Anthropic/Gemini) 버전 정의
- `[ ]` [Task] `build.gradle.kts`에 Spring AI starter 의존성 추가
- `[ ]` [Task] `application.yml`에 AI 관련 API Key(환경변수 처리 `${AI_API_KEY}`) 및 모델명 설정 추가

**🎯 Goal: RAG 기반 AI 질의 파이프라인(Controller/Service) 구현**
- `[ ]` [Task] 프론트엔드로부터 사용자 질의를 받는 `AiQueryRequest` DTO 생성
- `[ ]` [Task] AI의 응답 텍스트를 담을 `AiQueryResponse` DTO 생성
- `[ ]` [Task] `AiQueryRequest`를 받아 처리하는 `AiQueryController` REST API 엔드포인트 생성
- `[ ]` [Task] `AiQueryController`에서 호출할 `AiQueryService` 인터페이스 및 구현체 생성
- `[ ]` [Task] `AiQueryService` 내에서 `ChatClient` 빈을 주입받아 LLM API 호출 및 프롬프트 전달 로직 작성
- `[ ]` [Task] API 호출 실패(Timeout, Quota 초과 등) 시 적절한 Fallback 에러 메시지를 반환하는 예외 처리(`try-catch` 또는 `@Recover`) 구현

---

## Epic 3. High Priority: Core Feature Implementation (Data Seeding & Universal WAL)

### [Story 3.1] Universal WAL (Write-Ahead Logging) & Decision Record Foundation

**🎯 Goal: 전 모듈 불변 WAL(Write-Ahead Logging) 및 의사결정 추적 시스템 구축**
- `[ ]` [Task] 모든 모듈의 데이터 변경 및 이벤트를 수정/삭제 불가한 불변(Immutable) 로그로 기록하는 전역 WAL(Write-Ahead Logging) 인터페이스/엔티티 설계
- `[ ]` [Task] Spring Modulith 이벤트 리스너를 활용하여 각 모듈(baseline, field, progress, valuation)에서 발생하는 도메인 이벤트를 WAL 스키마에 자동 적재하는 공통 로직 구현
- `[ ]` [Task] 의사결정의 근거, 내용, 예상 결과를 기록하는 `DecisionRecord` 엔티티 설계 (`insights` 모듈)
- `[ ]` [Task] 특정 의사결정 시점의 WorkGraph 컨텍스트와 WAL 상태를 묶어 스냅샷으로 기록(Audit Trail)하는 로직 구현

### [Story 3.2] Mock Data Generation Pipeline

**🎯 Goal: Mock 데이터 생성 기반 설정**
- `[ ]` [Task] `build.gradle.kts`에 `net.datafaker:datafaker` 의존성 추가
- `[ ]` [Task] `application.yml`에 Seeder 실행 여부를 제어하는 `seed.enabled=true` 프로퍼티 추가

**🎯 Goal: Baseline / Field / Progress 순차 데이터 생성 로직**
- `[ ]` [Task] Baseline 모듈에 `BaselineDataSeeder` 컴포넌트 생성 및 `ApplicationRunner` 인터페이스 구현
- `[ ]` [Task] `BaselineDataSeeder` 내 Datafaker를 활용한 가상의 `Project` 엔티티 100건 생성 및 저장 로직 구현
- `[ ]` [Task] `BaselineDataSeeder` 내 가상의 `Task` 엔티티 무작위 생성 로직 구현
- `[ ]` [Task] Field 모듈에 `FieldDataSeeder` 컴포넌트 생성 및 `@Order(2)` 지정
- `[ ]` [Task] `FieldDataSeeder` 내 가상의 `Worker`(작업자), `Equipment`(장비) 데이터 무작위 생성 로직 구현
- `[ ]` [Task] `FieldDataSeeder` 내 일별 작업 일지(`DailyLog`) 데이터 무작위 생성 로직 구현
- `[ ]` [Task] Progress 모듈에 `ProgressDataSeeder` 컴포넌트 생성 및 `@Order(3)` 지정
- `[ ]` [Task] `ProgressDataSeeder` 내 일일 공정률(Progress Rate) 무작위 계산 로직 구현
- `[ ]` [Task] `ProgressDataSeeder` 내 작업 지연(Delay) 발생 여부를 무작위로 생성 및 저장하는 로직 구현

---

## Epic 4. Medium Priority: Infrastructure & Architecture Setup

### [Story 4.1] Spring Security & Auth (JWT/OAuth2)

**🎯 Goal: Spring Security 및 JWT 인증 기반 구축**
- `[ ]` [Task] `build.gradle.kts`에 `spring-boot-starter-security`, `jjwt-api`, `jjwt-impl`, `jjwt-jackson` 추가
- `[ ]` [Task] `SecurityConfig` 클래스 생성, `@EnableWebSecurity` 적용 및 `SessionCreationPolicy.STATELESS` 설정 추가
- `[ ]` [Task] `SecurityConfig` 내 CORS 설정 빈(`CorsConfigurationSource`)을 추가하여 프론트엔드 연동 지원
- `[ ]` [Task] `SecurityConfig` 내 `SecurityFilterChain` Bean 등록 (허용할 URI와 인증 필요 URI 분리)
- `[ ]` [Task] `AuthController` 생성 및 로그인(Login), 회원가입(Signup) 메서드 뼈대 작성
- `[ ]` [Task] JWT 토큰을 생성(sign)하고 검증(verify)하는 `JwtProvider` 유틸리티 클래스 구현

**🎯 Goal: 필터 및 인가(Authorization) 로직 적용**
- `[ ]` [Task] Request Header에서 `Bearer` 토큰을 추출하는 `JwtAuthenticationFilter` 클래스(`OncePerRequestFilter` 상속) 작성
- `[ ]` [Task] `JwtAuthenticationFilter`를 `SecurityConfig`의 필터 체인에 추가
- `[ ]` [Task] `UserDetailsService` 인터페이스를 구현하는 커스텀 `CustomUserDetailsService` 클래스 작성
- `[ ]` [Task] 특정 엔드포인트에 `@PreAuthorize("hasRole('ADMIN')")` 어노테이션을 적용하고 테스트 코드 작성

### [Story 4.2] Architectural Constraints Enforcement (ArchUnit)

**🎯 Goal: 외부 의존성 및 네이밍 룰 검증 (ArchUnit)**
- `[ ]` [Task] `build.gradle.kts` 내 Spring Boot 의존성 BOM 버전을 가져오는 스크립트 작성 (버전 검증용)
- `[ ]` [Task] ArchUnit을 사용해 모든 외부 라이브러리 버전이 BOM과 일치하는지 검사하는 테스트 클래스 작성
- `[ ]` [Task] DTO 패키지 내 클래스명이 `Dto`, `Request`, `Response`로 끝나는지 검사하는 ArchUnit 룰 작성
- `[ ]` [Task] Controller 클래스명이 `Controller` 또는 `Api`로 끝나는지 검사하는 ArchUnit 룰 작성
- `[ ]` [Task] Entity 클래스가 웹/서비스 계층 어노테이션을 가지지 않도록 검증하는 룰 작성

**🎯 Goal: 계층 간 의존성 및 코딩 컨벤션 검증**
- `[x]` [Task] Define layer-specific structural constraints. (Completed)
- `[x]` [Task] Restrict dependencies between layers. (Completed)
- `[x]` [Task] Refactor hardcoded strings to constants. (Completed)

### [Story 4.3] Architecture Review & Alignment

**🎯 Goal: 모듈 간 아키텍처 일관성 및 이벤트 분리 리뷰**
- `[ ]` [Task] 모든 모듈(baseline, field 등) 하위에 패키지 구조가 동일한지 검증하는 ArchUnit 룰 추가
- `[ ]` [Task] 각 모듈의 `package-info.java` 파일에 Spring Modulith `@ApplicationModule` 검증 테스트 작성
- `[ ]` [Task] 모듈 간 통신용 `event` 패키지 분리 및 외부 노출 제한(package-private) 룰 작성
- `[ ]` [Task] 모듈 간 통신이 무조건 이벤트(`ApplicationEventPublisher`)를 통해 일어나도록(타 모듈의 Service 빈 직접 주입 금지) 강제하는 ArchUnit 테스트 룰 작성
- `[ ]` [Task] `build.gradle.kts`에 `spring-modulith-docs` 의존성 추가 및 Gradle Task 설정을 통해 모듈 의존성 다이어그램(PlantUML) 자동 생성

### [Story 4.4] Multi-Environment Module Configuration (New)

**🎯 Goal: 툴체인 및 다중 환경 구성, 의존성 관리**
- `[ ]` [Task] 신규 라이브러리 추가 시 무조건 `gradle/libs.versions.toml`에 먼저 정의하도록 프로젝트 가이드 문서(`README.md`) 업데이트
- `[ ]` [Task] 루트 `build.gradle.kts`에 `java { toolchain { languageVersion.set(JavaLanguageVersion.of(25)) } }` 설정 추가

---

## Epic 5. Medium Priority: Test Infrastructure & Refactoring

### [Story 5.1] Testing Environment Overhaul

**🎯 Goal: 테스트 데이터 격리(DB 롤백) 유틸리티 작성**
- `[ ]` [Task] DB 상태를 롤백시키기 위해 모든 테이블의 TRUNCATE 쿼리를 실행하는 `TestDatabaseCleaner` 유틸리티 클래스 작성
- `[ ]` [Task] 통합 테스트 Base 클래스에 `@AfterEach`를 사용해 `TestDatabaseCleaner.execute()` 호출 설정
- `[ ]` [Task] `@SpringBootTest` 적용 Controller/Repository 테스트를 `@WebMvcTest`/`@DataJpaTest` 기반 슬라이스 테스트로 리팩토링

**🎯 Goal: 단위 테스트 중복 제거 및 시간 모킹 제어**
- `[ ]` [Task] 테스트에 자주 쓰이는 객체를 기본값으로 생성해주는 `FixtureFactory` 유틸리티 클래스 생성
- `[ ]` [Task] 반복되는 경계값 테스트에 `@ParameterizedTest`와 `@CsvSource` 어노테이션을 적용하여 코드 중복 제거
- `[ ]` [Task] 하드코딩된 `LocalDateTime.now()` 로직을 `java.time.Clock` 빈을 사용하도록 리팩토링 및 ArchUnit 룰 작성
- `[x]` [Task] Split file into multiple test classes. (Completed)
- `[x]` [Task] Set dependsOn for bootRun to test task. (Completed)

---

## Epic 6. Low Priority: Performance & Scalability

### [Story 6.1] Redis Integration & Global Caching

**🎯 Goal: Redis 인프라 구축 및 기본 설정**
- `[ ]` [Task] `gradle/libs.versions.toml`에 `spring-boot-starter-data-redis` 정의 및 `build.gradle.kts` 추가
- `[ ]` [Task] `application.yml`에 Redis 접속 정보(`spring.data.redis.host`, `port`) 추가
- `[ ]` [Task] `RedisConfig` 클래스 생성 (ConnectionFactory 및 `RedisTemplate<String, Object>` 빈 등록)
- `[ ]` [Task] `CacheConfig` 클래스 생성, `@EnableCaching` 선언 및 `RedisCacheManager` (TTL 포함) 설정

**🎯 Goal: API 캐싱 및 리프레시 토큰 관리**
- `[ ]` [Task] `RefreshToken` 데이터를 Redis에 저장하기 위한 엔티티(`@RedisHash`) 생성 및 Repository 구현
- `[ ]` [Task] Insights 모듈의 조회용 API 메서드에 `@Cacheable` 적용 및 데이터 수정 시 `@CacheEvict` 처리

### [Story 6.2] Spring Batch Setup

**🎯 Goal: Spring Batch 모듈 셋업 및 일일 공정률 배치 잡 구현**
- `[ ]` [Task] 프로젝트 루트에 `apps/pathfinder-batch` 서브모듈 디렉토리 생성 및 `settings.gradle.kts` 등록
- `[ ]` [Task] Batch 모듈에 `spring-boot-starter-batch` 의존성 추가 및 `BatchApplication` 메인 클래스 작성
- `[ ]` [Task] Spring Boot 3.x 방식 배치 설정을 위한 `BatchConfig` 클래스 작성
- `[ ]` [Task] 일일 공정률 집계를 위한 `DailyProgressJobConfig` 클래스 생성 및 Job / Step (Reader, Processor, Writer) 구현

### [Story 6.3] Database Monitoring & Health Checks

**🎯 Goal: Actuator 헬스체크 및 DB 커넥션/쿼리 모니터링**
- `[ ]` [Task] `gradle/libs.versions.toml`에 `spring-boot-starter-actuator` 정의 및 의존성 추가
- `[ ]` [Task] `application.yml`에 `/actuator/health` 엔드포인트 노출 및 `show-details=always` 속성 추가
- `[ ]` [Task] `application.yml`에 HikariCP 통계 노출 설정 추가
- `[ ]` [Task] `application.yml`에 Hibernate Slow Query 로깅 설정 추가

---

## Epic 7. Lowest Priority: Code Quality & Conventions Enforcement

### [Story 7.1] Linting & Formatting

**🎯 Goal: Spotless 포맷팅 파이프라인 구축**
- `[ ]` [Task] 루트 `build.gradle.kts`에 `com.diffplug.spotless` 플러그인 추가 및 Google Java Format 지정
- `[ ]` [Task] 터미널에서 `./gradlew spotlessApply` 명령어를 실행하여 전체 소스코드 포맷팅 일괄 수정
- `[ ]` [Task] Git `pre-commit` 훅을 생성하여 커밋 전 `./gradlew spotlessCheck` 자동 실행 스크립트 작성

### [Story 7.2] Coding Style Enforcement (ArchUnit)

**🎯 Goal: Spring Data JPA 및 기타 세부 컨벤션 강제(ArchUnit)**
- `[x]` [Task] Enforce strict naming convention & general coding style constraints. (Completed)
- `[ ]` [Task] Spring Data JPA Repository 메서드명이 `find`, `get`, `exists`, `count`, `delete`로만 시작하도록 룰 작성
- `[ ]` [Task] 애플리케이션 코드 내 `System.out.println` 및 `System.err.println` 사용 금지 룰 작성
- `[ ]` [Task] 서비스 인터페이스 구현체 이름이 `Impl` 접미사를 가지도록 강제하는 룰 작성

### [Story 7.3] Documentation Standardization (New)

**🎯 Goal: 문서 자동 포맷팅 및 체크리스트 관리**
- `[ ]` [Task] `docs` 폴더 내 비 Markdown 파일 식별 후 Pandoc 활용해 `.md` 파일로 변환하는 스크립트 작성
- `[ ]` [Task] KISA 소프트웨어 보안약점 진단가이드 체크리스트를 포함한 `docs/security-checklist.md` 생성

---

## Epic 8. Lowest Priority: CI/CD, Telemetry & Security

### [Story 8.1] CI/CD Pipeline (GitHub Actions & GitOps)

**🎯 Goal: GitHub Actions 기반 지속적 통합 파이프라인 구축**
- `[ ]` [Task] `.github/workflows/ci.yml` 생성 (Java 환경 세팅, Gradle 캐싱, `./gradlew build` 테스트 스텝 작성)
- `[ ]` [Task] CI 빌드 완료 후 성공/실패 상태를 Slack Webhook URL로 전송하는 스텝 추가
- `[ ]` [Task] `.github/dependabot.yml` 생성 및 의존성 자동 업데이트 주기 설정

**🎯 Goal: GitOps (ArgoCD 연동 준비) 및 CD 파이프라인**
- `[ ]` [Task] 프로젝트 루트(또는 별도 매니페스트 저장소)에 `k8s` 디렉토리 생성 후 Helm Chart 또는 Kustomize 기본 템플릿(Deployment, Service) 작성
- `[ ]` [Task] `docker-build.yml`에서 Docker Image Push 성공 시 `k8s` 디렉토리 내의 이미지 태그 버전을 자동 업데이트(Commit & Push)하는 스텝 추가 (ArgoCD 트리거 목적)

### [Story 8.2] Centralized Logging & Traceability

**🎯 Goal: 로그 포맷 및 MDC 로깅**
- `[ ]` [Task] `src/main/resources/logback-spring.xml` 생성 및 Console/RollingFile 로깅 포맷 정의
- `[ ]` [Task] 모든 HTTP 요청 헤더에서 Trace ID를 추출하여 MDC에 주입하는 `MdcLoggingFilter` 구현

### [Story 8.3] Telemetry & Distributed Tracing

**🎯 Goal: 분산 트레이싱(Zipkin) 및 메트릭 수집(Prometheus)**
- `[ ]` [Task] `build.gradle.kts`에 `micrometer-tracing-bridge-brave`, `zipkin-reporter-brave` 의존성 추가
- `[ ]` [Task] `application.yml`에 샘플링 확률 및 Zipkin 서버 URL 설정 추가
- `[ ]` [Task] `build.gradle.kts`에 `micrometer-registry-prometheus` 의존성 추가 및 `/actuator/prometheus` 오픈
- `[ ]` [Task] 로컬 `docker-compose.yml`에 Zipkin UI 서버 컨테이너 설정 추가
- `[ ]` [Task] `application.yml`에 수집된 트레이스 데이터를 로컬 Zipkin 서버로 전송하도록 `management.zipkin.tracing.endpoint` 설정

### [Story 8.4] Modulith Event Fallback & Security

**🎯 Goal: 이벤트 Fallback 및 템플릿/보안 검사 자동화**
- `[x]` [Task] Spring Modulith 이벤트를 활용한 모듈 간 통신(EDA) 보완 및 장애 격리 (Partially Completed)
- `[ ]` [Task] 프로젝트 루트에 `.github/PULL_REQUEST_TEMPLATE.md` 및 `ISSUE_TEMPLATE` 파일 생성
- `[ ]` [Task] `.github/workflows/codeql-analysis.yml` 생성 및 CodeQL 보안 취약점 스캔 스텝 추가

### [Story 8.5] Human Inspection & Quality Gate

**🎯 Goal: 품질 게이트(Quality Gate) 및 수동 리뷰 강제화**
- `[ ]` [Task] GitHub `main` 브랜치에 대해 `Require a pull request before merging` (최소 1명 리뷰) 룰 적용

---

## Epic 9. Cloud Infrastructure & MSA Deployment (AWS)

### [Story 9.1] Containerization & Artifact Registry

**🎯 Goal: 도커라이징 및 ECR 배포 파이프라인**
- `[ ]` [Task] 프로젝트 루트에 Multi-stage `Dockerfile` 작성 및 `USER 1001` 권한 제한 추가
- `[ ]` [Task] `.github/workflows/docker-build.yml` 생성 (aws-actions 설정 후 ECR build & push 스텝 작성)

### [Story 9.2] Infrastructure as Code (IaC)

**🎯 Goal: AWS 네트워크 및 리소스 인프라 코드화(Terraform)**
- `[ ]` [Task] `infrastructure/aws/terraform` 폴더 생성 후 `main.tf`, `variables.tf` 작성
- `[ ]` [Task] `vpc.tf` 파일을 생성해 네트워크 리소스 작성
- `[ ]` [Task] `rds.tf` (PostgreSQL) 및 `ecs.tf` (ECS Cluster/Fargate Task) 리소스 정의 파일 작성

### [Story 9.3] Secret Management

**🎯 Goal: AWS Secrets Manager 연동**
- `[ ]` [Task] `build.gradle.kts`에 `spring-cloud-starter-aws-secrets-manager-config` 추가
- `[ ]` [Task] `bootstrap.yml` 생성 후 `aws-secretsmanager:` 설정 추가 및 `application.yml` 민감 정보 치환

### [Story 9.4] Fatal Alerting & Incident Response (Server Crash)

**🎯 Goal: 클라우드 장애 모니터링 및 알림 파이프라인**
- `[ ]` [Task] Terraform `cloudwatch.tf` 파일에 ECS Task CPU 80% 초과 시 작동하는 알람 리소스 추가
- `[ ]` [Task] Terraform `cloudwatch.tf` 파일에 ECS Task Memory 80% 초과 시 작동하는 알람 리소스 추가
- `[ ]` [Task] Terraform `cloudwatch.tf` 파일에 Target Group의 HTTP 5xx 에러율 증가(다운타임) 모니터링 알람 리소스 추가
- `[ ]` [Task] Terraform `cloudwatch.tf` 파일에 ALB / NAT Gateway 네트워크 트래픽 급증(부하) 모니터링 알람 리소스 추가
- `[ ]` [Task] Terraform `sns.tf` 파일에 알람 전송용 SNS Topic 리소스 추가 및 Chatbot 연동 문서 작성
- `[ ]` [Task] 애플리케이션 에러 로그 발생 시 CloudWatch 메트릭 필터 기반 알람을 트리거하는 Terraform 코드 작성

---

## Epic 10. SRE & Deep Infrastructure (Icebox)

### [Story 10.1] eBPF & OS Level Monitoring
**🎯 Goal: 커널 레벨 네트워크 및 리소스 병목 추적**
- `[ ]` [Task] eBPF 기반 네트워크 패킷 캡처 및 HTTP 지연율 실시간 모니터링 데몬 설정 리서치
- `[ ]` [Task] Node Exporter를 활용하여 EKS/EC2 노드의 디스크 I/O 틱, TCP Connection Drop 메트릭 수집 및 Prometheus 연동
- `[ ]` [Task] 실시간 시스템 로그(`dmesg`, `syslog`) 파싱을 위한 커스텀 쉘 스크립트 작성 및 알람 연동

# Project Backlog (Step-by-Step Task Breakdown)

## Epic 0. Absolute Priority: Real-time Developer Experience (DevEx) & Continuous Profiling

// TO-DO 제미나이, 클로드 코드 같이 사용시 스레드 락처럼 문맥을 나누는 방법 혹은 더 나은방법
// TO-DO 제미나이나 클로드코드가 쉘 사용시 쉘체크를 하는 방법. 제미나이 훅?일거같음. 프롬프팅 도중에 이를 체크하고 쉘을 수정하는 방법 찾기
// TO-DO 런타임,컴파일 타임 검증도구들 더 찾고, AI와 함께 코딩을한다면 어떤 지표들이 더 필요한가 검색하기.

### [Story 0.1] Real-time Quality & Performance Feedback Tools

- `[ ]` [Task] `build.gradle.kts`에 `jacoco` 플러그인 추가 및 테스트 실행 시 실시간 커버리지 측정 환경 구축
- `[ ]` [Task] JaCoCo 테스트 커버리지 최소 기준(Threshold, 예: Line 80%, Branch 70%) 강제 설정 (100% 목표) 및 빌드 검증
  파이프라인 연동
- `[ ]` [Task] IDE(IntelliJ) 환경에 SonarLint 도입 및 팀원 간 엄격한 정적 분석 룰셋(Rule Set) 통일
- `[ ]` [Task] 로컬 개발 환경용 SonarQube(또는 SonarCloud) 서버 연동 및 정적 코드 분석/품질 게이트(Quality Gate) 자동화 설정
- `[ ]` [Task] JDK Flight Recorder(JFR)를 활용한 로컬 개발 환경의 실시간 CPU/Memory/Thread 프로파일링 설정
- `[ ]` [Task] 로컬 애플리케이션 실행 중 성능 병목을 실시간으로 확인하기 위한 JVM 모니터링 도구(Async-profiler, VisualVM, JProfiler
  등) 도입 리서치 및 적용
- `[ ]` [Task] Apache JMeter 또는 Gatling을 연동하여 로컬 환경에서 API 병목 및 부하 테스트 실시간 모니터링 환경 구축
- `[ ]` [Task] JRebel 또는 DCEVM 같은 Hot-Swap 도구 리서치하여 코드 수정 즉시(Zero-restart) 반영되는 실시간 프로그래밍 환경 세팅

### [Story 0.2] Advanced Runtime Observability & Control

- `[ ]` [Task] **Heap Dump & Runtime Snapshot:** 런타임 중 특정 조건(OOM 전조, 특정 이벤트) 발생 시 자동으로 Heap Dump를
  생성하고 관리하는 유틸리티 구현
- `[ ]` [Task] **Dynamic Log Level Management:** Spring Actuator 또는 커스텀 엔드포인트를 통해 서버 재시작 없이 실시간으로
  Logging Level(INFO -> DEBUG)을 조정하는 기능 구현
- `[ ]` [Task] **Quota-aware Execution Control:** AI API(Gemini 등)의 Quota 상태를 모니터링하여,
  `Resource Exhausted` 발생 시 작업을 일시 중지하거나(Pause) 속도를 조절하는(Throttling) 제어 로직 구현

---

## Epic 1. Highest Priority: Core Domain (AI-Driven BIM Work Graph Query System)

### [Story 1.1] IFC Data Ingestion & Graph Modeling

- `[ ]` [Task] IFC 파싱을 위한 Java 라이브러리(IfcOpenShell 등) 리서치 및 `build.gradle.kts` 추가
- `[ ]` [Task] `IfcWall`, `IfcSlab` 등 개별 객체들을 파싱하여 공종(Trade) 또는 작업 단위(Task)로 묶는 `WorkGroup` Entity
  설계
- `[ ]` [Task] 그룹핑된 IFC 객체 군단 간의 선행/후행 작업 의존성을 나타내는 작업 그래프(DAG) 구조를 Java Class로 모델링

### [Story 1.2] LLM Context Preparation & Serialization

- `[ ]` [Task] 메모리에 구축된 작업 그래프 객체(Java Class)들을 AI가 구조적으로 이해하기 쉬운 형태(JSON, YAML, 또는 Graph 형태의 문자열)로
  직렬화(Serialize)하는 유틸리티 구현
- `[ ]` [Task] 직렬화된 데이터와 함께 "이 공종이 지연되면 크리티컬 패스(Critical Path)는 어떻게 바뀌어?", "이 작업에 할당된 벽체들의 총 체적은
  얼마야?" 등을 묻기 위한 프롬프트 템플릿(Prompt Template) 설계

### [Story 1.3] AI Query Integration (Spring AI / LangChain)

- `[ ]` [Task] `build.gradle.kts`에 `spring-ai` (또는 `langchain4j`) 의존성을 추가하여 OpenAI/Claude API 연동 설정
- `[ ]` [Task] 프론트엔드에서 자연어 질의를 받아, 백엔드에서 객체 그래프 컨텍스트(Context)를 RAG 방식으로 주입한 후 AI에게 질의하고 답변을 반환하는
  REST API 컨트롤러 구현

---

## Epic 2. High Priority: Core Feature Implementation (Data Seeding)

### [Story 2.1] Mock Data Generation Pipeline

- `[ ]` [Story] `BaselineDataSeeder`: Datafaker 객체를 활용해 Project, Task 등 초기 기본 설정 데이터 100건 생성 및 DB
  insert 로직 구현
- `[ ]` [Story] `FieldDataSeeder`: 생성된 Baseline 데이터를 바탕으로 가상의 작업자, 건설장비, 작업 일지 데이터 무작위 생성 로직 구현
- `[ ]` [Story] `ProgressDataSeeder`: Field 데이터를 기반으로 공정률(Progress Rate), 지연 상태(Delay) 등을 계산한 임시 데이터
  생성
- `[ ]` [Task] 위 3개의 Seeder가 올바른 순서(Baseline -> Field -> Progress)로 실행되도록 ApplicationRunner 등록

---

## Epic 3. Medium Priority: Infrastructure & Architecture Setup

### [Story 3.1] Database & Schema Integration

- `[ ]` [Task] `application.yaml`의 5개 다중 데이터소스 설정을 단일 데이터소스(e.g., PostgreSQL/H2)로 통합
- `[ ]` [Task] 통합된 단일 DB 연결을 사용하는 공통 `DataSource` 빈(Bean) 생성
- `[ ]` [Task] 각 모듈의 `PersistenceConfig` 파일에서 다중 DB 설정 제거 및 공통 `DataSource` 주입받도록 수정
- `[ ]` [Task] 각 모듈 Entity 패키지에 `@Table(schema = "모듈명")` 지정하여 물리적 DB 대신 논리적 스키마(Schema)로 데이터 분리 강제

### [Story 3.2] Database Migration (Flyway/Liquibase)

- `[ ]` [Task] `build.gradle.kts`에 Flyway(또는 Liquibase) 의존성 추가
- `[ ]` [Task] 각 모듈 스키마(baseline, field 등) 생성을 위한 V1 초기 마이그레이션 스크립트 작성
- `[ ]` [Task] Spring Boot 구동 시 Flyway가 각 모듈 스키마별로 마이그레이션을 실행하도록 환경 설정

### [Story 3.3] Spring Security & Auth (JWT/OAuth2)

- `[ ]` [Task] `build.gradle.kts`에 `spring-boot-starter-security`, `jjwt` 의존성 추가
- `[ ]` [Task] `SecurityConfig.java` 생성 및 세션 상태 Stateless(무상태)로 설정
- `[ ]` [Task] 로그인/회원가입 처리용 AuthController 및 AuthService 뼈대 작성
- `[ ]` [Task] JWT 발급(Access/Refresh) 및 서명 검증 유틸리티 클래스 구현
- `[ ]` [Task] HTTP Request에서 JWT를 추출하고 검증하는 커스텀 `JwtAuthenticationFilter` 구현 및 Security 필터 체인에 등록
- `[ ]` [Task] 사용자 권한(Role)별 API 접근 제어 규칙 설정

### [Story 3.4] Architectural Constraints Enforcement (ArchUnit)

- `[ ]` [Task] Spring Boot Starter BOM 버전을 파싱하여 모든 의존성이 동일한 BOM 버전을 따르는지 검증하는 ArchUnit 테스트 작성
- `[ ]` [Task] DTO 클래스는 `*Dto` 또는 `*Response`, `*Request`로 끝나야 한다는 ArchUnit 규칙 추가
- `[ ]` [Task] Controller는 `*Controller` 또는 `*Api`로 끝나야 한다는 ArchUnit 규칙 추가
- `[ ]` [Task] Domain Object(Entity, VO)는 Jpa 관련 어노테이션이나 Core 어노테이션 외의 Web/Service 레이어 의존성을 가지지 못하게
  제한
- `[x]` [Task] Define layer-specific structural constraints. (Applies to: DTO, Domain Object,
  Service, Repository, Controller) *(Completed)*
- `[ ]` [Task] Restrict dependencies between Controller -> Service -> Repository -> Domain layers
  within 각 package.
- `[x]` [Task] Refactor all hardcoded string values to be programmable/dynamic constants. *(
  Completed)*

### [Story 3.5] Architecture Review & Alignment

- `[ ]` [Task] 모든 모듈(baseline, field, progress, valuation, insights)에 대해 동일한 계층 구조(Layer Structure)가
  빠짐없이 일관되게 적용되었는지 크로스 체크 및 동기화
- `[ ]` [Task] `event`와 `listener`를 별도의 패키지/계층으로 나누는 현재의 방식이 사람이 읽기 편한(유지보수성) 구조인지, 아니면 AI가 코드를 분석하고
  컨텍스트를 파악하기 편한 구조인지 아키텍처 관점에서 재평가(Review)

---

## Epic 4. Medium Priority: Test Infrastructure & Refactoring

### [Story 4.1] Testing Environment Overhaul

- `[ ]` [Task] 각 테스트 클래스마다 DB 상태를 롤백시키기 위한 `@Transactional` 또는 커스텀 `@AfterEach` truncate 쿼리 로직 작성 (
  Ensure test isolation)
- `[ ]` [Task] 테스트 환경 구동 속도 최적화를 위해 `@SpringBootTest` 대신 슬라이스 테스트(`@DataJpaTest`, `@WebMvcTest`)로 전환
  가능한 클래스들 식별 및 리팩토링
- `[ ]` [Task] `TestEntityManager` 또는 전용 `Fixture` 클래스를 활용하여 반복되는 테스트 데이터 Setup/Teardown 코드를 공통
  유틸리티로 추출
- `[ ]` [Task] 비즈니스 로직 경계값(Boundary) 테스트를 위해 `@ParameterizedTest`와 `@CsvSource`/`@MethodSource`를
  적용하여 반복 테스트 케이스 통합
- `[ ]` [Task] 하드코딩된 `LocalDateTime.now()` 등을 `java.time.Clock` 빈으로 교체하여 테스트에서 시간을 Mocking할 수 있도록
  제약(ArchUnit) 및 리팩토링
- `[x]` [Task] Split file into multiple test classes for better organization and maintainability. *(
  Completed)*

---

## Epic 5. Low Priority: Performance & Scalability

### [Story 5.1] Redis Integration & Global Caching

- `[ ]` [Task] `build.gradle.kts`에 `spring-boot-starter-data-redis` 의존성 추가
- `[ ]` [Task] `RedisConfig.java` 작성 (RedisConnectionFactory, RedisTemplate 빈 등록 및 직렬화 설정)
- `[ ]` [Task] `CacheConfig.java` 작성 (RedisCacheManager 세팅 및 TTL 설정)
- `[ ]` [Task] JWT Refresh Token을 Redis에 저장/조회하는 Repository 로직 구현
- `[ ]` [Task] Insights 모듈 등 조회 빈도가 높은 API 서비스 메서드에 `@Cacheable`, `@CacheEvict` 어노테이션 적용

### [Story 5.2] Spring Batch Setup

- `[ ]` [Task] 루트 디렉터리에 `apps/pathfinder-batch` 신규 멀티 모듈 디렉토리 생성 및 `build.gradle.kts` 설정
- `[ ]` [Task] 루트 `settings.gradle.kts`에 `:apps:pathfinder-batch` 등록
- `[ ]` [Task] Batch 전용 애플리케이션 진입점(`@EnableBatchProcessing`) 클래스 작성
- `[ ]` [Task] 일일 공정률(Progress) 집계를 위한 첫 번째 Batch Job 및 Step(ItemReader, Processor, Writer) 구현

### [Story 5.3] Database Monitoring & Health Checks (New)

- `[ ]` [Task] Spring Boot Actuator (`spring-boot-starter-actuator`) 의존성 추가
- `[ ]` [Task] `/actuator/health` 엔드포인트를 통한 단일 DB 인스턴스 Liveness/Readiness 헬스 체크 설정
- `[ ]` [Task] HikariCP 커넥션 풀 메트릭스(Active, Idle, Total connections) 활성화 및 로깅 설정
- `[ ]` [Task] 데이터베이스 병목 추적을 위한 Slow Query 로깅 활성화 및 알림 연동

---

## Epic 6. Lowest Priority: Code Quality & Conventions Enforcement

### [Story 6.1] Linting & Formatting

- `[ ]` [Task] `build.gradle.kts`에 `com.diffplug.spotless` 플러그인 추가
- `[ ]` [Task] Spotless 포맷팅 규칙(Google Java Format 등) 적용 및 `./gradlew spotlessApply` 파이프라인 구성

### [Story 6.2] Coding Style Enforcement (ArchUnit)

- `[x]` [Task] Enforce strict naming convention constraints across all components. *(Completed)*
- `[x]` [Task] Enforce naming conventions for test classes and methods. *(Completed)*
- `[x]` [Task] Enforce general Java coding style constraints. *(Completed)*
- `[ ]` [Task] Add naming and return-type constraints for Spring Data JPA Repository methods.
- `[ ]` [Task] Restrict the usage of specific external library functions.
- `[ ]` [Task] Extract and apply useful advanced rules from the official ArchUnit documentation.

---

## Epic 7. Lowest Priority: CI/CD, Telemetry & Security

### [Story 7.1] CI/CD Pipeline

- `[ ]` [Task] `.github/workflows/ci.yml` 생성 및 Java 버전에 맞는 Build & Test 자동화 Action 스크립트 작성
- `[ ]` [Task] 성공적인 CI 빌드 후 Slack/Discord 등으로 테스트 결과를 전송하는 알림(Alerting) 스크립트 연동

### [Story 7.2] Error Handling & Logging

- `[ ]` [Task] 전역 예외 처리를 위한 `@RestControllerAdvice` 클래스 생성
- `[ ]` [Task] Spring 3+ 표준인 ProblemDetail(RFC 7807) 객체를 반환하도록 커스텀 Exception Handler 메서드 구현
- `[ ]` [Task] `src/main/resources/logback-spring.xml` 생성 및 콘솔/파일 로깅 패턴 포맷 표준화
- `[ ]` [Task] API 요청마다 고유한 Trace ID(traceId, spanId)를 부여하도록 MDC(Mapped Diagnostic Context) 필터 구현

### [Story 7.3] Telemetry & Distributed Tracing

- `[ ]` [Task] `micrometer-tracing-bridge-brave` 및 `zipkin-reporter-brave` 의존성 추가
- `[ ]` [Task] `application.yaml`에 Zipkin 서버 연동 설정 및 샘플링 확률(Sampling Rate) 지정
- `[ ]` [Task] Prometheus 메트릭 수집을 위해 `spring-boot-starter-actuator` 추가 및 `/actuator/prometheus`
  엔드포인트 오픈

### [Story 7.4] Modulith Event Fallback & Security

- `[x]` [Task] Spring Modulith 이벤트를 활용한 모듈 간 통신(EDA) 보완 및 장애 격리(Fallback/DLQ) 로직 *(Partially
  Completed)*
- `[ ]` [Task] `.github/PULL_REQUEST_TEMPLATE.md` 및 `ISSUE_TEMPLATE.md` 템플릿 파일 생성
- `[ ]` [Task] CI 파이프라인에 Mythos 또는 깃허브 Dependabot/CodeQL 연동하여 보안 취약점 스캔(SAST) 단계 추가

### [Story 7.5] Human Inspection & Quality Gate

- `[ ]` [Task] 자동화된 아키텍처 테스트(ArchUnit) 및 CI/CD 파이프라인을 통과하더라도, 반드시 사람(전문가)이 개입하여 코드의 도메인 의미와 구조적 합리성을
  직접 인스펙션(Manual Code Review)하는 단계 공식화

---

## Epic 8. Cloud Infrastructure & MSA Deployment (AWS)

### [Story 8.1] Containerization & Artifact Registry

- `[ ]` [Task] Spring Boot 애플리케이션의 최적화된 Docker 이미지 생성을 위한 Multi-stage `Dockerfile` (또는 Cloud Native
  Buildpacks) 작성
- `[ ]` [Task] GitHub Actions에 AWS ECR (Elastic Container Registry) 빌드 및 푸시 자동화 파이프라인 구축

### [Story 8.2] Infrastructure as Code (IaC)

- `[ ]` [Task] AWS CloudFormation (또는 Terraform)을 사용하여 VPC, Subnet, Security Group 등 기본 네트워크 인프라 코드화
- `[ ]` [Task] 데이터베이스(RDS/Aurora PostgreSQL) 및 ElastiCache(Redis) 프로비저닝 스크립트 작성
- `[ ]` [Task] 애플리케이션 구동을 위한 AWS ECS(Fargate) 또는 EKS 클러스터 프로비저닝 스크립트 작성

### [Story 8.3] Secret Management

- `[ ]` [Task] `application.yaml`의 민감 정보(DB 패스워드, JWT 시크릿, API Key 등)를 AWS Secrets Manager 또는
  Parameter Store로 이관 및 연동

### [Story 8.4] Fatal Alerting & Incident Response (Server Crash)

- `[ ]` [Task] AWS CloudWatch 연동하여 ECS/EKS 컨테이너의 CPU/Memory 임계치 초과 및 OOM(Out Of Memory) 모니터링 대시보드 구축
- `[ ]` [Task] 서버 애플리케이션 다운(Crash), 재시작 루프(CrashLoopBackOff), 또는 5xx 에러율 급증 시 AWS SNS + AWS Chatbot을
  활용해 사내 Slack/Discord 채널로 PagerDuty(긴급 알림) 발송 로직 구현

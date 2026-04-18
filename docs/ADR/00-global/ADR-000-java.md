# ADR [000]: java 25 도입

## 상태 (Status)

- 승인됨(Accepted)

## 맥락 (Context)

1. 대규모 데이터 시뮬레이션의 메모리 부하: BIM 엔티티는 수만개에서 수백만 개의 객체를 생성하며, 이는 힙 메모리와 GC(Garbage Collection)에 큰 부담을
   줍니다.
2. 가상 스레드(Virtual Thread)의 성숙도: Java 21에서 도입된 가사 스레드를 본 격적으로 활용하고 있으나, 스레드간 불변 데이터를 공유(ThreadLocal
   대체) 하거나 계층적 작업을 관리하는데 더 정교한 도구가 필요합니다.
3. 도메인 복잡성 대응: IFC 4.3 표준의 상속 구조와 복잡한 타입을 처리하기 위해 더 유연한 생성자 로직과 패턴 매칭 기능이 요구됩니다.
4. 시뮬레이션 처리량(Throughput) 최적화: 데이터 시딩(Seeding) 및 대규모 검색 시 발생하는 대기 시간을 줄이기 위해 런타임 차원의 성능 개선이 필요합니다.

## 결정 (Decision)

- 프로젝트의 안정성과 성능을 모두 확보하기 위해 최신 Java 25 LTS 버전을 표준 런타임으로 채택합니다.

1. Compact Object Headers (JEP 519) 활성화: Project Lilliput의 결과물인 압축 객체 헤더를 사용하여 BIM 객체들의 메모리 점유율을 약
   10~20% 절감합니다.
2. Scoped Values & Structured Concurrency 활용: ThreadLocal 대신 Scoped Values를 사용하여 가상 스레드 간 컨텍스트 전달을
   최적화하고, Structured Concurrency로 시뮬레이션 하위 작업의 생명주기를 안전하게 관리합니다.
3. Flexible Constructor Bodies 사용: 생성자 내에서 super() 호출 전 검증 로직을 배치하여 IFC 부재 데이터의 정합성을 초기 단계에서 확보합니다.
4. Generational Shenandoah GC 적용: 대용량 힙 메모리 환경에서도 중단 시간(Pause Time)을 최소화하여 실시간 질의응답(LLM Q&A)의 반응성을
   보장합니다.

## 결과 (Consequences)

긍정적 효과 (Positive)
메모리 효율성: 객체 헤더 압축을 통해 동일한 하드웨어 자원에서 더 많은 BIM 부재를 시뮬레이션할 수 있습니다.
코드 안정성: 구조적 동시성을 통해 가상 스레드에서 발생하는 리소스 누수나 예외 처리 오류를 획기적으로 방지합니다.
성능 향상: 최신 GC 최적화와 원시 타입 패턴 매칭을 통해 수치 계산 및 데이터 검색 속도가 향상됩니다.
장기 지원: LTS 버전이므로 향후 수년간 보안 패치와 안정적인 유지보수가 보장됩니다.

부정적 효과 및 리스크 (Negative/Risks)
라이브러리 호환성: 일부 오래된 외부 라이브러리가 Java 25의 바이트코드를 인식하지 못할 수 있어 사전 검증이 필요합니다. (특히 ByteBuddy, ASM 관련 라이브러리)
학습 곡선: Scoped Values나 Structured Concurrency 같은 새로운 동시성 패러다임에 대한 팀원들의 적응 기간이 필요합니다.
JVM 옵션 튜닝: 압축 객체 헤더 등 일부 기능은 명시적인 JVM 옵션 활성화가 필요하며, 이에 대한 벤치마킹 비용이 발생합니다.

## Fact-checking (gemini-deep-research)

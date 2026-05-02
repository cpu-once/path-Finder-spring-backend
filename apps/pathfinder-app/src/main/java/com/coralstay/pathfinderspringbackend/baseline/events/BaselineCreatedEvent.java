package com.coralstay.pathfinderspringbackend.baseline.events;

/**
 * Baseline 데이터가 생성되었음을 타 모듈에 알리기 위한 비동기 이벤트 객체
 * 모듈 간 느슨한 결합(Loose Coupling)을 위해 사용됩니다.
 */
public record BaselineCreatedEvent(String baselineId, String message) {
}

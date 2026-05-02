package com.coralstay.pathfinderspringbackend.insights.events;

/**
 * 통계/분석(Insight) 데이터가 생성되었음을 알리는 이벤트 (현재 구독하는 모듈 없음)
 */
public record InsightsGeneratedEvent(String insightId, String reportUrl) {
}

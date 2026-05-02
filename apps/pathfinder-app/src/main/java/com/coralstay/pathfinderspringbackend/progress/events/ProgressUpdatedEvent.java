package com.coralstay.pathfinderspringbackend.progress.events;

/**
 * 공정률(Progress)이 업데이트되었음을 알리는 이벤트
 */
public record ProgressUpdatedEvent(String progressId, int currentPercentage, String message) {
}

package com.coralstay.pathfinderspringbackend.field.event;

/**
 * Field 데이터(현장 작업, 장비 배치 등)가 생성되었음을 알리는 이벤트
 */
public record FieldCreatedEvent(String fieldId, String message) {
}

package com.coralstay.pathfinderspringbackend.field.service;

import com.coralstay.pathfinderspringbackend.field.events.FieldCreatedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FieldService {

    private final ApplicationEventPublisher eventPublisher;

    public FieldService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public void initializeFieldData(String baselineId) {
        System.out.println("[FieldService] 현장 작업(Field) 기초 데이터 세팅 완료 (ID: " + baselineId + ")");

        // 로직 처리 후 자체 이벤트 발행
        eventPublisher.publishEvent(new FieldCreatedEvent("F-" + baselineId, "현장 작업 준비 완료"));
    }
}

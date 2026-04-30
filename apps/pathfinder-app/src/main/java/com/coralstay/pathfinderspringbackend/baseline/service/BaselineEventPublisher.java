package com.coralstay.pathfinderspringbackend.baseline.service;

import com.coralstay.pathfinderspringbackend.baseline.event.BaselineCreatedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BaselineEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public BaselineEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public void createBaselineAndNotify(String baselineId) {
        // 1. 여기서 Baseline 도메인 로직 처리 및 DB 저장
        System.out.println("Baseline DB 저장 완료: " + baselineId);

        // 2. 다른 모듈(Progress, Insights 등)에 비동기 이벤트 발행
        BaselineCreatedEvent event = new BaselineCreatedEvent(baselineId, "새로운 Baseline이 생성되었습니다.");
        eventPublisher.publishEvent(event);
    }
}

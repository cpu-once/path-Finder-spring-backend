package com.coralstay.pathfinderspringbackend.progress.service;

import com.coralstay.pathfinderspringbackend.progress.events.ProgressUpdatedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProgressService {

    private final ApplicationEventPublisher eventPublisher;

    public ProgressService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public void initializeProgressTracking(String baselineId, String message) {
        System.out.println("[ProgressService] " + baselineId + "에 대한 공정 트래킹을 시작합니다. (사유: " + message + ")");

        // 로직 처리 후 자체 이벤트 발행
        eventPublisher.publishEvent(new ProgressUpdatedEvent("P-001", 10, "공정 트래킹 활성화 됨"));
    }
}

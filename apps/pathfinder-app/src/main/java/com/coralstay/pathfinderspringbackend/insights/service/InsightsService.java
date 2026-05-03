package com.coralstay.pathfinderspringbackend.insights.service;

import com.coralstay.pathfinderspringbackend.insights.events.InsightsGeneratedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@Service
public class InsightsService {

    private final ApplicationEventPublisher eventPublisher;

    public InsightsService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public void updateDashboard(String valuationId, BigDecimal finalCost) {
        System.out.println("[InsightsService] AI 대시보드 업데이트 완료 (ID: " + valuationId + ", 금액: " + finalCost + ")");

        // 로직 처리 후 자체 이벤트 발행
        eventPublisher.publishEvent(new InsightsGeneratedEvent("I-" + valuationId, "http://report.url/latest"));
    }
}

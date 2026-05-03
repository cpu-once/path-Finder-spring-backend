package com.coralstay.pathfinderspringbackend.valuation.service;

import com.coralstay.pathfinderspringbackend.valuation.events.ValuationCompletedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@Service
public class ValuationService {

    private final ApplicationEventPublisher eventPublisher;

    public ValuationService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public void prepareValuation(String progressId, int currentPercentage) {
        System.out.println("[ValuationService] 공정률 " + currentPercentage + "% 반영. 단가 산출 시작 (ID: " + progressId + ")");

        // 로직 처리 후 자체 이벤트 발행
        eventPublisher.publishEvent(new ValuationCompletedEvent("V-" + progressId, new BigDecimal("1000000"), "정산 완료"));
    }
}

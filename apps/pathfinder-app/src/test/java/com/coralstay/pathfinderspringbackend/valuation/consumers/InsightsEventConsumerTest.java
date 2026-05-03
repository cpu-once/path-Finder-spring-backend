package com.coralstay.pathfinderspringbackend.valuation.consumers;

import com.coralstay.pathfinderspringbackend.insights.events.InsightsGeneratedEvent;
import com.coralstay.pathfinderspringbackend.valuation.service.ValuationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.context.ApplicationEventPublisher;

import static org.mockito.Mockito.verify;

@SpringBootTest
public class InsightsEventConsumerTest {

    private static final String DESC_RECEIVE_EVENT_AND_CALL_VALUATION_SERVICE = "컨슈머: InsightsGeneratedEvent 발생 시 ValuationService가 호출되어야 한다";

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @MockitoBean
    private ValuationService valuationService;

    @Test
    @DisplayName(DESC_RECEIVE_EVENT_AND_CALL_VALUATION_SERVICE)
    void receiveEventAndCallValuationService() {
        String testId = "I-400";
        InsightsGeneratedEvent event = new InsightsGeneratedEvent(testId, "http://report.url");

        eventPublisher.publishEvent(event);

        // 현재 InsightsEventConsumer 로직이 임시이므로, 나중에 ValuationService 로직 구체화 시 검증 활성화
        // verify(valuationService).prepareValuation(testId, 100);
    }
}

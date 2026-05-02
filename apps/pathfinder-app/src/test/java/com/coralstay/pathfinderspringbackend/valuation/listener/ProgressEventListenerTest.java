package com.coralstay.pathfinderspringbackend.valuation.listener;

import com.coralstay.pathfinderspringbackend.progress.event.ProgressUpdatedEvent;
import com.coralstay.pathfinderspringbackend.valuation.service.ValuationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.context.ApplicationEventPublisher;

import static org.mockito.Mockito.verify;

@SpringBootTest
public class ProgressEventListenerTest {

    private static final String DESC_RECEIVE_EVENT_AND_CALL_VALUATION_SERVICE = "리스너: ProgressUpdatedEvent 발생 시 ValuationService가 호출되어야 한다";

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @MockitoBean
    private ValuationService valuationService;

    @Test
    @DisplayName(DESC_RECEIVE_EVENT_AND_CALL_VALUATION_SERVICE)
    void receiveEventAndCallValuationService() {
        String testId = "P-200";
        int percentage = 50;
        ProgressUpdatedEvent event = new ProgressUpdatedEvent(testId, percentage, "테스트용 공정률 업데이트");

        // 컨텍스트에 강제로 이벤트 발행
        eventPublisher.publishEvent(event);

        // 리스너가 이벤트를 수신하여 ValuationService를 호출했는지 검증
        verify(valuationService).prepareValuation(testId, percentage);
    }
}

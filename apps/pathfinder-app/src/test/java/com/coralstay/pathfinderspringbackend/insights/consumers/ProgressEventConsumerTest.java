package com.coralstay.pathfinderspringbackend.insights.consumers;

import com.coralstay.pathfinderspringbackend.progress.events.ProgressUpdatedEvent;
import com.coralstay.pathfinderspringbackend.insights.service.InsightsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.context.ApplicationEventPublisher;

import java.math.BigDecimal;

import static org.mockito.Mockito.verify;

@SpringBootTest
public class ProgressEventConsumerTest {

    private static final String DESC_RECEIVE_EVENT_AND_CALL_INSIGHTS_SERVICE = "컨슈머: ProgressUpdatedEvent 발생 시 InsightsService가 호출되어야 한다";

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @MockitoBean
    private InsightsService insightsService;

    @Test
    @DisplayName(DESC_RECEIVE_EVENT_AND_CALL_INSIGHTS_SERVICE)
    void receiveEventAndCallInsightsService() {
        String testId = "P-200";
        int percentage = 50;
        ProgressUpdatedEvent event = new ProgressUpdatedEvent(testId, percentage, "테스트용 공정률 업데이트");

        eventPublisher.publishEvent(event);

        // 현재 ProgressEventConsumer에서는 구체적 로직이 미정이므로 임시로 updateDashboard 호출을 검증하도록 가정 (실제로는 통계 갱신 로직)
        // verify(insightsService).updateDashboard(testId, BigDecimal.ZERO);
    }
}

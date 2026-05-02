package com.coralstay.pathfinderspringbackend.insights.listener;

import static org.mockito.Mockito.verify;

import com.coralstay.pathfinderspringbackend.insights.service.InsightsService;
import com.coralstay.pathfinderspringbackend.valuation.event.ValuationCompletedEvent;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
public class ValuationEventListenerTest {

  private static final String DESC_RECEIVE_EVENT_AND_CALL_INSIGHTS_SERVICE = "리스너: ValuationCompletedEvent 발생 시 InsightsService가 호출되어야 한다";

  @Autowired
  private ApplicationEventPublisher eventPublisher;

  @MockitoBean
  private InsightsService insightsService;

  @Test
  @DisplayName(DESC_RECEIVE_EVENT_AND_CALL_INSIGHTS_SERVICE)
  void receiveEventAndCallInsightsService() {
    String testId = "V-300";
    BigDecimal finalCost = new BigDecimal("1500000");
    ValuationCompletedEvent event = new ValuationCompletedEvent(testId, finalCost, "테스트용 단가 산출 완료");

    // 컨텍스트에 강제로 이벤트 발행
    eventPublisher.publishEvent(event);

    // 리스너가 이벤트를 수신하여 InsightsService를 호출했는지 검증
    verify(insightsService).updateDashboard(testId, finalCost);
  }
}

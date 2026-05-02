package com.coralstay.pathfinderspringbackend.field.listener;

import static org.mockito.Mockito.verify;

import com.coralstay.pathfinderspringbackend.baseline.event.BaselineCreatedEvent;
import com.coralstay.pathfinderspringbackend.field.service.FieldService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
public class BaselineEventListenerTest {

  private static final String DESC_RECEIVE_EVENT_AND_CALL_FIELD_SERVICE = "리스너: BaselineCreatedEvent 발생 시 FieldService가 호출되어야 한다";

  @Autowired
  private ApplicationEventPublisher eventPublisher;

  @MockitoBean
  private FieldService fieldService;

  @Test
  @DisplayName(DESC_RECEIVE_EVENT_AND_CALL_FIELD_SERVICE)
  void receiveEventAndCallFieldService() {
    String testId = "B-100";
    String message = "테스트용 이벤트 발행";
    BaselineCreatedEvent event = new BaselineCreatedEvent(testId, message);

    // 컨텍스트에 강제로 이벤트 발행
    eventPublisher.publishEvent(event);

    // 리스너가 이벤트를 수신하여 FieldService를 호출했는지 검증
    verify(fieldService).initializeFieldData(testId);
  }
}

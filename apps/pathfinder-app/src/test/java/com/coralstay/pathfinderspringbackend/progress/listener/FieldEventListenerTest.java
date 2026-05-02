package com.coralstay.pathfinderspringbackend.progress.listener;

import static org.mockito.Mockito.verify;

import com.coralstay.pathfinderspringbackend.field.event.FieldCreatedEvent;
import com.coralstay.pathfinderspringbackend.progress.service.ProgressService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
public class FieldEventListenerTest {

  private static final String DESC_RECEIVE_EVENT_AND_CALL_PROGRESS_SERVICE = "리스너: FieldCreatedEvent 발생 시 ProgressService가 호출되어야 한다";

  @Autowired
  private ApplicationEventPublisher eventPublisher;

  @MockitoBean
  private ProgressService progressService;

  @Test
  @DisplayName(DESC_RECEIVE_EVENT_AND_CALL_PROGRESS_SERVICE)
  void receiveEventAndCallProgressService() {
    String testId = "F-100";
    String message = "현장 작업 생성 완료";
    FieldCreatedEvent event = new FieldCreatedEvent(testId, message);

    // 컨텍스트에 강제로 이벤트 발행
    eventPublisher.publishEvent(event);

    // 리스너가 이벤트를 수신하여 ProgressService를 호출했는지 검증 (현재 로직에서는 initializeProgressTracking을 임시로 사용)
    verify(progressService).initializeProgressTracking(testId, message);
  }
}

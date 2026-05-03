package com.coralstay.pathfinderspringbackend.progress.consumers;

import com.coralstay.pathfinderspringbackend.field.events.FieldCreatedEvent;
import com.coralstay.pathfinderspringbackend.progress.service.ProgressService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.context.ApplicationEventPublisher;

import static org.mockito.Mockito.verify;

@SpringBootTest
public class FieldEventConsumerTest {

    private static final String DESC_RECEIVE_EVENT_AND_CALL_PROGRESS_SERVICE = "컨슈머: FieldCreatedEvent 발생 시 ProgressService가 호출되어야 한다";

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

        eventPublisher.publishEvent(event);

        verify(progressService).initializeProgressTracking(testId, message);
    }
}

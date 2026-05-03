package com.coralstay.pathfinderspringbackend.progress.consumers;

import com.coralstay.pathfinderspringbackend.baseline.events.BaselineCreatedEvent;
import com.coralstay.pathfinderspringbackend.progress.service.ProgressService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.context.ApplicationEventPublisher;

import static org.mockito.Mockito.verify;

@SpringBootTest
public class BaselineEventConsumerTest {

    private static final String DESC_RECEIVE_EVENT_AND_CALL_SERVICE = "컨슈머: BaselineCreatedEvent 발생 시 ProgressService가 호출되어야 한다";

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @MockitoBean
    private ProgressService progressService;

    @Test
    @DisplayName(DESC_RECEIVE_EVENT_AND_CALL_SERVICE)
    void receiveEventAndCallService() {
        String testId = "B-3003";
        String message = "테스트용 이벤트 발행";
        BaselineCreatedEvent event = new BaselineCreatedEvent(testId, message);

        eventPublisher.publishEvent(event);

        verify(progressService).initializeProgressTracking(testId, message);
    }
}

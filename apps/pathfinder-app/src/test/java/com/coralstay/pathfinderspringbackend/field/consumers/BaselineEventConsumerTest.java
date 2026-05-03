package com.coralstay.pathfinderspringbackend.field.consumers;

import com.coralstay.pathfinderspringbackend.baseline.events.BaselineCreatedEvent;
import com.coralstay.pathfinderspringbackend.field.service.FieldService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.context.ApplicationEventPublisher;

import static org.mockito.Mockito.verify;

@SpringBootTest
public class BaselineEventConsumerTest {

    private static final String DESC_RECEIVE_EVENT_AND_CALL_FIELD_SERVICE = "컨슈머: BaselineCreatedEvent 발생 시 FieldService가 호출되어야 한다";

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

        eventPublisher.publishEvent(event);

        verify(fieldService).initializeFieldData(testId);
    }
}

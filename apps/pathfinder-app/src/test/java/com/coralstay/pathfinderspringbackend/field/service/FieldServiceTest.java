package com.coralstay.pathfinderspringbackend.field.service;

import com.coralstay.pathfinderspringbackend.field.events.FieldCreatedEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.modulith.test.PublishedEvents;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationModuleTest
public class FieldServiceTest {

    private static final String DESC_INITIALIZE_FIELD_DATA_AND_PUBLISH_EVENT = "서비스: Field 초기화 시 이벤트가 정상적으로 발행되어야 한다";

    @Autowired
    private FieldService fieldService;

    @Test
    @DisplayName(DESC_INITIALIZE_FIELD_DATA_AND_PUBLISH_EVENT)
    void initializeFieldDataAndPublishEvent(PublishedEvents events) {
        String testId = "B-100";
        fieldService.initializeFieldData(testId);

        var matchingEvents = events.ofType(FieldCreatedEvent.class)
                .matching(event -> event.fieldId().equals("F-" + testId));

        assertThat(matchingEvents).hasSize(1);
    }
}

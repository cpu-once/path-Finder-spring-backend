package com.coralstay.pathfinderspringbackend.baseline.service;

import com.coralstay.pathfinderspringbackend.baseline.event.BaselineCreatedEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.modulith.test.PublishedEvents;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationModuleTest
public class BaselineServiceTest {

    private static final String DESC_CREATE_BASELINE_AND_PUBLISH_EVENT = "서비스: Baseline 생성 시 이벤트가 정상적으로 발행되어야 한다";

    @Autowired
    private BaselineService baselineService;

    @Test
    @DisplayName(DESC_CREATE_BASELINE_AND_PUBLISH_EVENT)
    void createBaselineAndPublishEvent(PublishedEvents events) {
        String testId = "B-2002";
        baselineService.createBaseline(testId);

        // 발행된 이벤트 중 BaselineCreatedEvent가 있는지 확인
        var matchingEvents = events.ofType(BaselineCreatedEvent.class)
                .matching(event -> event.baselineId().equals(testId));

        assertThat(matchingEvents).hasSize(1);
    }
}

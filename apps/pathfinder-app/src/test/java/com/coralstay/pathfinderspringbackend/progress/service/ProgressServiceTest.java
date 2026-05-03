package com.coralstay.pathfinderspringbackend.progress.service;

import com.coralstay.pathfinderspringbackend.progress.events.ProgressUpdatedEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.modulith.test.PublishedEvents;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationModuleTest
public class ProgressServiceTest {

    private static final String DESC_INITIALIZE_PROGRESS_TRACKING_AND_PUBLISH_EVENT = "서비스: Progress 시작 시 이벤트가 정상적으로 발행되어야 한다";

    @Autowired
    private ProgressService progressService;

    @Test
    @DisplayName(DESC_INITIALIZE_PROGRESS_TRACKING_AND_PUBLISH_EVENT)
    void initializeProgressTrackingAndPublishEvent(PublishedEvents events) {
        String testId = "F-100";
        progressService.initializeProgressTracking(testId, "Test message");

        var matchingEvents = events.ofType(ProgressUpdatedEvent.class)
                .matching(event -> event.progressId().equals("P-001"));

        assertThat(matchingEvents).hasSize(1);
    }
}

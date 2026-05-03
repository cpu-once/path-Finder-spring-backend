package com.coralstay.pathfinderspringbackend.insights.service;

import com.coralstay.pathfinderspringbackend.insights.events.InsightsGeneratedEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.modulith.test.PublishedEvents;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationModuleTest
public class InsightsServiceTest {

    private static final String DESC_UPDATE_DASHBOARD_AND_PUBLISH_EVENT = "서비스: Insights 통계 갱신 시 이벤트가 정상적으로 발행되어야 한다";

    @Autowired
    private InsightsService insightsService;

    @Test
    @DisplayName(DESC_UPDATE_DASHBOARD_AND_PUBLISH_EVENT)
    void updateDashboardAndPublishEvent(PublishedEvents events) {
        String valuationId = "V-300";
        insightsService.updateDashboard(valuationId, new BigDecimal("1000000"));

        var matchingEvents = events.ofType(InsightsGeneratedEvent.class)
                .matching(event -> event.insightId().equals("I-" + valuationId));

        assertThat(matchingEvents).hasSize(1);
    }
}

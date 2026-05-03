package com.coralstay.pathfinderspringbackend.valuation.service;

import com.coralstay.pathfinderspringbackend.valuation.events.ValuationCompletedEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.modulith.test.PublishedEvents;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@ApplicationModuleTest
public class ValuationServiceTest {

    private static final String DESC_PREPARE_VALUATION_AND_PUBLISH_EVENT = "서비스: Valuation 산출 완료 시 이벤트가 정상적으로 발행되어야 한다";

    @Autowired
    private ValuationService valuationService;

    @Test
    @DisplayName(DESC_PREPARE_VALUATION_AND_PUBLISH_EVENT)
    void prepareValuationAndPublishEvent(PublishedEvents events) {
        String progressId = "P-200";
        valuationService.prepareValuation(progressId, 100);

        var matchingEvents = events.ofType(ValuationCompletedEvent.class)
                .matching(event -> event.valuationId().equals("V-" + progressId));

        assertThat(matchingEvents).hasSize(1);
    }
}

package com.coralstay.pathfinderspringbackend.valuation.consumers;

import com.coralstay.pathfinderspringbackend.progress.events.ProgressUpdatedEvent;
import com.coralstay.pathfinderspringbackend.valuation.service.ValuationService;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
public class ProgressEventConsumer {

    private final ValuationService valuationService;

    public ProgressEventConsumer(ValuationService valuationService) {
        this.valuationService = valuationService;
    }

    @ApplicationModuleListener
    void onProgressUpdated(ProgressUpdatedEvent event) {
        System.out.println("[Valuation Module] 공정률 " + event.currentPercentage() + "% 도달 감지. 기성금 및 예상 비용 산출을 준비합니다. (ID: " + event.progressId() + ")");
        valuationService.prepareValuation(event.progressId(), event.currentPercentage());
    }
}

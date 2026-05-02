package com.coralstay.pathfinderspringbackend.insights.listener;

import com.coralstay.pathfinderspringbackend.insights.service.InsightsService;
import com.coralstay.pathfinderspringbackend.valuation.event.ValuationCompletedEvent;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
public class ValuationEventListener {

    private final InsightsService insightsService;

    public ValuationEventListener(InsightsService insightsService) {
        this.insightsService = insightsService;
    }

    @ApplicationModuleListener
    void onValuationCompleted(ValuationCompletedEvent event) {
        System.out.println("[Insights Module] 단가 산출 완료 감지. 대시보드 통계용 데이터를 갱신합니다. (ID: " + event.valuationId() + ", 금액: " + event.finalCost() + ")");
        insightsService.updateDashboard(event.valuationId(), event.finalCost());
    }
}

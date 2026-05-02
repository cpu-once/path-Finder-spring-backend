package com.coralstay.pathfinderspringbackend.insights.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@Service
public class InsightsService {

    @Transactional
    public void updateDashboard(String valuationId, BigDecimal finalCost) {
        System.out.println("[InsightsService] AI 대시보드 업데이트 완료 (ID: " + valuationId + ", 금액: " + finalCost + ")");
    }
}

package com.coralstay.pathfinderspringbackend.valuation.event;

import java.math.BigDecimal;

/**
 * 기성고/단가 계산(Valuation)이 완료되었음을 알리는 이벤트
 */
public record ValuationCompletedEvent(String valuationId, BigDecimal finalCost, String message) {
}

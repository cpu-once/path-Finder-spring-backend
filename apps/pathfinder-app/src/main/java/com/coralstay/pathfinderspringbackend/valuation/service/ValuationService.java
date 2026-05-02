package com.coralstay.pathfinderspringbackend.valuation.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ValuationService {

    @Transactional
    public void prepareValuation(String progressId, int currentPercentage) {
        System.out.println("[ValuationService] 공정률 " + currentPercentage + "% 반영. 단가 산출 시작 (ID: " + progressId + ")");
    }
}

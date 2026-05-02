package com.coralstay.pathfinderspringbackend.field.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FieldService {

    @Transactional
    public void initializeFieldData(String baselineId) {
        System.out.println("[FieldService] 현장 작업(Field) 기초 데이터 세팅 완료 (ID: " + baselineId + ")");
    }
}

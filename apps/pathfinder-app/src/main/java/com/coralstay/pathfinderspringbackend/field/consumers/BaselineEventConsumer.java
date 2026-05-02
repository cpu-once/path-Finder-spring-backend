package com.coralstay.pathfinderspringbackend.field.consumers;

import com.coralstay.pathfinderspringbackend.baseline.events.BaselineCreatedEvent;
import com.coralstay.pathfinderspringbackend.field.service.FieldService;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component("fieldBaselineEventConsumer")
public class BaselineEventConsumer {

    private final FieldService fieldService;

    public BaselineEventConsumer(FieldService fieldService) {
        this.fieldService = fieldService;
    }

    @ApplicationModuleListener
    void onBaselineCreated(BaselineCreatedEvent event) {
        System.out.println("[Field Module] Baseline 데이터 생성 감지. 현장 작업(Field) 기초 데이터 세팅을 준비합니다. (ID: " + event.baselineId() + ")");
        fieldService.initializeFieldData(event.baselineId());
    }
}

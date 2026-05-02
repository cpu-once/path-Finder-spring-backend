package com.coralstay.pathfinderspringbackend.progress.listener;

import com.coralstay.pathfinderspringbackend.field.event.FieldCreatedEvent;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
public class FieldEventListener {

    @ApplicationModuleListener
    void onFieldCreated(FieldCreatedEvent event) {
        System.out.println("[Progress Module] Field 작업 할당 감지. 해당 작업의 공정률 트래킹을 활성화합니다. (ID: " + event.fieldId() + ")");
        // ProgressService 호출
    }
}

package com.coralstay.pathfinderspringbackend.progress.consumers;

import com.coralstay.pathfinderspringbackend.field.events.FieldCreatedEvent;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
public class FieldEventConsumer {

    @ApplicationModuleListener
    void onFieldCreated(FieldCreatedEvent event) {
        System.out.println("[Progress Module] Field 작업 할당 감지. 해당 작업의 공정률 트래킹을 활성화합니다. (ID: " + event.fieldId() + ")");
        // ProgressService 호출
    }
}

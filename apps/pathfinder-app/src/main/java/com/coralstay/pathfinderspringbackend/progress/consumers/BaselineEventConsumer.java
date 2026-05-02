package com.coralstay.pathfinderspringbackend.progress.consumers;

import com.coralstay.pathfinderspringbackend.baseline.events.BaselineCreatedEvent;
import com.coralstay.pathfinderspringbackend.progress.service.ProgressService;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component("progressBaselineEventConsumer")
public class BaselineEventConsumer {

    private final ProgressService progressService;

    public BaselineEventConsumer(ProgressService progressService) {
        this.progressService = progressService;
    }

    /**
     * Spring Modulith가 제공하는 @ApplicationModuleListener를 사용하여
     * 트랜잭션이 성공적으로 커밋된 이후(AFTER_COMMIT)에 비동기적으로 이벤트를 처리합니다.
     */
    @ApplicationModuleListener
    void onBaselineCreated(BaselineCreatedEvent event) {
        System.out.println("[Progress Module] Baseline 생성 이벤트 수신 완료!");

        // Progress 관련 서비스 로직 호출
        progressService.initializeProgressTracking(event.baselineId(), event.message());
    }
}

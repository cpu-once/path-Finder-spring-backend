package com.coralstay.pathfinderspringbackend.progress.listener;

import com.coralstay.pathfinderspringbackend.baseline.event.BaselineCreatedEvent;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
public class BaselineEventListener {

    /**
     * Spring Modulith가 제공하는 @ApplicationModuleListener를 사용하여
     * 트랜잭션이 성공적으로 커밋된 이후(AFTER_COMMIT)에 비동기적으로 이벤트를 처리합니다.
     * 이를 통해 Baseline 트랜잭션과 Progress 트랜잭션을 분리하여 안전성을 확보합니다.
     */
    @ApplicationModuleListener
    void onBaselineCreated(BaselineCreatedEvent event) {
        System.out.println("[Progress Module] Baseline 생성 이벤트 수신 완료!");
        System.out.println("전달받은 데이터: " + event.baselineId() + ", 메시지: " + event.message());

        // 여기서 Progress 관련 DB 로직 수행 (별도의 트랜잭션 적용)
    }
}

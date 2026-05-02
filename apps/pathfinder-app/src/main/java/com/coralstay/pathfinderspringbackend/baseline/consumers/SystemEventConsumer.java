package com.coralstay.pathfinderspringbackend.baseline.consumers;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class SystemEventConsumer {

    /**
     * Baseline 모듈은 하위 의존성이 없으므로 다른 모듈의 이벤트를 구독하지 않습니다.
     * 본 클래스는 시스템 레벨 이벤트(Spring Boot 구동 완료 등)를 구독하거나
     * 내부 패키지 구조 유지를 위해 작성되었습니다.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        System.out.println("[Baseline Module] 애플리케이션 구동 완료. Baseline 데이터 준비 상태를 체크합니다.");
    }
}

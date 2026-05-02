package com.coralstay.pathfinderspringbackend.baseline.listener;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.boot.SpringApplication;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SystemEventListenerTest {

    private static final String DESC_SYSTEM_EVENT_LISTENER = "리스너: ApplicationReadyEvent가 정상적으로 수신되어야 한다";

    @Autowired
    private SystemEventListener systemEventListener;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private ConfigurableApplicationContext context;

    @Test
    @DisplayName(DESC_SYSTEM_EVENT_LISTENER)
    void receiveApplicationReadyEvent() {
        // SystemEventListener가 스프링 컨텍스트에 정상 등록되어 있는지 확인
        assertThat(systemEventListener).isNotNull();

        // ApplicationReadyEvent 발생 시 예외 없이 동작하는지 호출 테스트
        ApplicationReadyEvent event = new ApplicationReadyEvent(new SpringApplication(), new String[0], context, Duration.ofMillis(100));
        eventPublisher.publishEvent(event);
    }
}

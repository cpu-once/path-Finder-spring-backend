package com.coralstay.pathfinderspringbackend.baseline.controller;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.coralstay.pathfinderspringbackend.baseline.service.BaselineService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BaselineController.class)
public class BaselineControllerTest {

  private static final String DESC_CREATE_BASELINE = "컨트롤러: Baseline 생성 요청 시 서비스가 정상 호출되어야 한다";

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private BaselineService baselineService;

  @Test
  @DisplayName(DESC_CREATE_BASELINE)
  void createBaseline() throws Exception {
    String testId = "B-1001";

    mockMvc.perform(post("/api/baselines").param("id", testId))
        .andExpect(status().isOk())
        .andExpect(content().string("Baseline created successfully: " + testId));

    verify(baselineService).createBaseline(testId);
  }
}

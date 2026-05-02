package com.coralstay.pathfinderspringbackend.baseline.controller;

import com.coralstay.pathfinderspringbackend.baseline.service.BaselineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/baselines")
public class BaselineController {

    private final BaselineService baselineService;

    public BaselineController(BaselineService baselineService) {
        this.baselineService = baselineService;
    }

    @PostMapping
    public ResponseEntity<String> createBaseline(@RequestParam String id) {
        baselineService.createBaseline(id);
        return ResponseEntity.ok("Baseline created successfully: " + id);
    }
}

package com.harsha.bfhl.controller;

import com.harsha.bfhl.dto.BfhlRequest;
import com.harsha.bfhl.dto.BfhlResponse;
import com.harsha.bfhl.exception.InvalidRequestException;
import com.harsha.bfhl.service.BfhlService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
public class BfhlController {

    private final BfhlService bfhlService;

    public BfhlController(BfhlService bfhlService) {
        this.bfhlService = bfhlService;
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> root() {
        Map<String, Object> response = new HashMap<>();
        response.put("service", "BFHL API");
        response.put("status", "UP");
        response.put("endpoints", Map.of(
            "health", "GET /health",
            "bfhl", "POST /bfhl"
        ));
        response.put("timestamp", System.currentTimeMillis());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "BFHL API");
        response.put("timestamp", System.currentTimeMillis());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/bfhl")
    public ResponseEntity<BfhlResponse> processData(@Valid @RequestBody(required = false) BfhlRequest request) {
        if (request == null) {
            throw new InvalidRequestException("Request body must not be null");
        }
        BfhlResponse response = bfhlService.processData(request);
        return ResponseEntity.ok(response);
    }
}

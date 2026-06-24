package com.harsha.bfhl.controller;

import com.harsha.bfhl.dto.BfhlRequest;
import com.harsha.bfhl.dto.BfhlResponse;
import com.harsha.bfhl.exception.InvalidRequestException;
import com.harsha.bfhl.service.BfhlService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bfhl")
public class BfhlController {

    private final BfhlService bfhlService;

    public BfhlController(BfhlService bfhlService) {
        this.bfhlService = bfhlService;
    }

    @PostMapping
    public ResponseEntity<BfhlResponse> processData(@Valid @RequestBody(required = false) BfhlRequest request) {
        if (request == null) {
            throw new InvalidRequestException("Request body must not be null");
        }
        BfhlResponse response = bfhlService.processData(request);
        return ResponseEntity.ok(response);
    }
}

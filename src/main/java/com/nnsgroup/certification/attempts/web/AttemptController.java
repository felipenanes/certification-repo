package com.nnsgroup.certification.attempts.web;

import com.nnsgroup.certification.attempts.domain.Attempt;
import com.nnsgroup.certification.attempts.service.AttemptService;
import com.nnsgroup.certification.attempts.web.dto.AttemptRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/attempts")
@RequiredArgsConstructor
public class AttemptController {

    private final AttemptService attemptService;

    @GetMapping("/{attemptId}")
    public Attempt getAttempt(@PathVariable UUID providerId) {
        return attemptService.getById(providerId);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody AttemptRequestDTO dto) {
        attemptService.create(dto);
        return ResponseEntity.ok().build();
    } //TODO: Aplicar Mapper ou Command e alterar o response para AttemptResponseDTO - use Record
}
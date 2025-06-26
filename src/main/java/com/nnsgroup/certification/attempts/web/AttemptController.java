package com.nnsgroup.certification.attempts.web;

import com.nnsgroup.certification.attempts.domain.Attempt;
import com.nnsgroup.certification.attempts.mapper.AttemptResponseMapper;
import com.nnsgroup.certification.attempts.service.AttemptService;
import com.nnsgroup.certification.attempts.web.request.AttemptRequestDTO;
import com.nnsgroup.certification.attempts.web.response.AttemptResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/attempts")
@RequiredArgsConstructor
public class AttemptController {

    private final AttemptService attemptService;
    private final AttemptResponseMapper attemptResponseMapper;

    @GetMapping("/{attemptId}")
    public ResponseEntity<AttemptResponseDTO> getAttempt(@PathVariable UUID attemptId) {
        Attempt attempt = attemptService.getById(attemptId);
        AttemptResponseDTO response = attemptResponseMapper.toDto(attempt);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<AttemptResponseDTO> create(@RequestBody AttemptRequestDTO dto) {
        Attempt attempt = attemptService.create(dto);
        AttemptResponseDTO response = attemptResponseMapper.toDto(attempt);

        URI location = URI.create("/attempts/" + attempt.getId());

        return ResponseEntity
            .created(location)
            .body(response);
    }
}
package com.sdarm.generation;

import com.sdarm.generation.dto.GenerationResponse;
import com.sdarm.generation.service.GenerationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenerationFacade {

    private final GenerationService generationService;

    public List<GenerationResponse> getAll() {
        return generationService.getAll().stream()
                .map(generation -> GenerationResponse.builder()
                        .id(generation.getId())
                        .name(generation.getName())
                        .timeOfRequest(Instant.now())
                        .build())
                .collect(Collectors.toList());
    }
}

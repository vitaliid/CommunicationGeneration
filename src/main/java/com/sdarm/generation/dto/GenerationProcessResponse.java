package com.sdarm.generation.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Builder
public class GenerationProcessResponse {

    private Long id;
    private Instant createdAt;
    private long duration;
    private Long generationId;
}

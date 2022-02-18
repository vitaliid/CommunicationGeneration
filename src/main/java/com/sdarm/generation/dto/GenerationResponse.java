package com.sdarm.generation.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Builder
@Data
public class GenerationResponse {

    private Long id;

    private String name;

    private Instant timeOfRequest;
}

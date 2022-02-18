package com.sdarm.generation.dto;

import com.sdarm.generation.domain.Gang;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Builder
@Data
public class GenerationResponse {

    private Long id;

    private String name;

    private Instant timeOfRequest;

    private List<Gang> gangs;
}

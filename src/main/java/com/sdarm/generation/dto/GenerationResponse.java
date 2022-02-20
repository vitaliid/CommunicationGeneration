package com.sdarm.generation.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Builder
@Getter
public class GenerationResponse {

    private Long id;

    private String name;

    private Instant timeOfRequest;

    private List<GangResponse> gangs;
}

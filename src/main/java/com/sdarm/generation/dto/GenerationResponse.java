package com.sdarm.generation.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Builder
@Getter
@Setter
public class GenerationResponse {

    private Long id;

    private String name;

    private Instant timeOfRequest;

    private List<GangResponse> gangs;

    private GenerationProcessResponse process;
}

package com.sdarm.generation.dto;

import com.sdarm.generation.domain.Gang;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class GenerationCreateRequest {

    private String name;

    private List<Gang> gangs;
}

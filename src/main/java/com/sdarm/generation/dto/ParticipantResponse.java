package com.sdarm.generation.dto;

import com.sdarm.generation.domain.Gender;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ParticipantResponse {

    private Long id;

    private String name;

    private Gender gender;
}

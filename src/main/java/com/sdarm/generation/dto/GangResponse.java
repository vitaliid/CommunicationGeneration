package com.sdarm.generation.dto;

import com.sdarm.generation.domain.Participant;
import lombok.Builder;
import lombok.Getter;

import java.util.List;


@Builder
@Getter
public class GangResponse {

    private Long id;

    private List<Participant> gangsters;
}

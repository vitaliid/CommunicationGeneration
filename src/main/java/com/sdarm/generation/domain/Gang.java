package com.sdarm.generation.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class Gang {

    private Long id;

    private Set<Participant> gangsters = new HashSet<>();
}

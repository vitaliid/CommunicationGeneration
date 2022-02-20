package com.sdarm.generation.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "generation_process")
public class GenerationProcess {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "duration")
    private Long duration;

    @Column(name = "algorithm")
    private Algorithm algorithm;

    @Column(name = "generation_id")
    private Long generationId;
}

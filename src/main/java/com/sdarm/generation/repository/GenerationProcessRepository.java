package com.sdarm.generation.repository;

import com.sdarm.generation.domain.GenerationProcess;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenerationProcessRepository extends JpaRepository<GenerationProcess, Long> {

    GenerationProcess findTopByOrderByCreatedAtDesc();

    GenerationProcess findByGenerationId(Long generationId);
}

package com.sdarm.generation.repository;

import com.sdarm.generation.domain.Generation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenerationRepository extends JpaRepository<Generation, Long> {

    Generation findTopByOrderByCreatedAtDesc();
}

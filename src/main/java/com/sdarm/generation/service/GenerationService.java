package com.sdarm.generation.service;

import com.sdarm.generation.domain.Algorithm;
import com.sdarm.generation.domain.Generation;

import java.util.List;

public interface GenerationService {

    List<Generation> getAll();

    Generation getById(Long id);

    Generation getLatest();

    Generation generate(Algorithm algorithm);

    Generation save(Generation generation);
}

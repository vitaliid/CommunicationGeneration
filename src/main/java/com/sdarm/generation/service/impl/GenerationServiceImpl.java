package com.sdarm.generation.service.impl;

import com.sdarm.generation.domain.Algorithm;
import com.sdarm.generation.domain.Generation;
import com.sdarm.generation.generator.CommunicationGenerator;
import com.sdarm.generation.generator.CommunicationGeneratorFactory;
import com.sdarm.generation.repository.GenerationRepository;
import com.sdarm.generation.service.GenerationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GenerationServiceImpl implements GenerationService {

    private final GenerationRepository generationRepository;
    private final CommunicationGeneratorFactory communicationGeneratorFactory;

    @Override
    public List<Generation> getAll() {
        return generationRepository.findAll();
    }

    @Override
    public Generation getById(Long id) {
        return generationRepository.findById(id).orElse(null);
    }

    @Override
    public Generation getLatest() {
        return generationRepository.findTopByOrderByCreatedAtDesc();
    }

    @Override
    public Generation generate(Algorithm algorithm) {
        final CommunicationGenerator communicationGenerator =
                communicationGeneratorFactory.getCommunicationGenerator(algorithm);

        Generation generation = communicationGenerator.generate();
        Collections.shuffle(generation.getGangs());
        return generation;
    }

    @Override
    public Generation save(Generation generation) {
        return generationRepository.save(generation);
    }


}

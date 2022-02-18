package com.sdarm.generation.service;

import com.sdarm.generation.domain.Algorithm;
import com.sdarm.generation.domain.Generation;
import com.sdarm.generation.generator.CommunicationGenerator;
import com.sdarm.generation.generator.CommunicationGeneratorFactory;
import com.sdarm.generation.repository.GenerationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        return generationRepository.getById(id);
    }

    @Override
    public Generation generate(Algorithm algorithm) {
        final CommunicationGenerator communicationGenerator =
                communicationGeneratorFactory.getCommunicationGenerator(algorithm);

        return communicationGenerator.generate();
    }


}

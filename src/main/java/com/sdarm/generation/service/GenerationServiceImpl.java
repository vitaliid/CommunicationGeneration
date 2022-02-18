package com.sdarm.generation.service;

import com.sdarm.generation.domain.Generation;
import com.sdarm.generation.repository.GenerationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenerationServiceImpl implements GenerationService {

    private final GenerationRepository generationRepository;

    @Override
    public List<Generation> getAll() {
        return generationRepository.findAll();
    }
}

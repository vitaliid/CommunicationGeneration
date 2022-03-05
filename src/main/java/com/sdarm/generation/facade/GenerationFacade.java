package com.sdarm.generation.facade;

import com.sdarm.generation.domain.Algorithm;
import com.sdarm.generation.domain.Gang;
import com.sdarm.generation.domain.Generation;
import com.sdarm.generation.domain.GenerationProcess;
import com.sdarm.generation.dto.*;
import com.sdarm.generation.repository.GenerationProcessRepository;
import com.sdarm.generation.service.GenerationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenerationFacade {

    private final GenerationService generationService;
    private final GenerationProcessRepository generationProcessRepository;

    public List<GenerationResponse> getAll() {
        return generationService.getAll().stream()
                .map(generation -> convert(generation, null))
                .collect(Collectors.toList());
    }

    public GenerationResponse getById(Long id) {
        Generation generation;
        GenerationProcess generationProcess = null;
        if (id == null || id == 0) {
            generation = generationService.generate(Algorithm.GENDER_IS_THE_SAME);
        } else {
            generation = generationService.getById(id);
            generationProcess = Optional.ofNullable(generation)
                    .map(Generation::getId)
                    .map(generationId -> generationProcessRepository.findByGenerationId(generation.getId()))
                    .orElse(null);
        }

        return convert(generation, generationProcess);
    }

    public GenerationResponse getLatest() {
        Generation generation = generationService.getLatest();
        GenerationProcess generationProcess = generationProcessRepository.findByGenerationId(generation.getId());
        if (generationProcess == null) {
            generationProcess = generationProcessRepository.findTopByOrderByCreatedAtDesc();
            if (generationProcess.getGenerationId() != null) {
                generationProcess = null;
            }
        }

        return convert(generation, generationProcess);
    }

    @Transactional
    public GenerationResponse create(GenerationCreateRequest request) {
        Generation generation = convert(request);
        generation = generationService.save(generation);
        log.info("Generation was successfully created in DB with id: {}", generation.getId());

        return convert(generation, null);
    }

    @Transactional
    public GenerationResponse generate(String name, Long duration, Algorithm algorithm, boolean prepared) {
        GenerationProcess generationProcess = new GenerationProcess();
        generationProcess.setDuration(duration);
        generationProcess.setAlgorithm(algorithm);
        generationProcess.setCreatedAt(Instant.now());
        generationProcess = generationProcessRepository.save(generationProcess);

        Generation generation = new Generation();
        if (prepared) {
            generation = generationService.generate(algorithm);
            generation = generationService.save(generation);
            generationProcess.setGenerationId(generation.getId());
            generationProcessRepository.save(generationProcess);
        }

        if (name != null) {
            generation.setName(name);
        }

        return convert(generation, generationProcess);
    }

    @Transactional
    public GenerationResponse finish(Long processId, GenerationProcessFinalizeRequest request) {
        final GenerationProcess generationProcess;
        Generation generation = null;

        if (processId != null) {
            generationProcess = generationProcessRepository.findById(processId).orElse(null);
        } else {
            generationProcess = generationProcessRepository.findTopByOrderByCreatedAtDesc();
        }

        if (generationProcess != null && generationProcess.getGenerationId() == null && request.getGenerationId() != null) {
            generation = generationService.getById(request.getGenerationId());
            if (generation != null) {
                generationProcess.setGenerationId(request.getGenerationId());
                generationProcessRepository.save(generationProcess);
            }
            generationProcess.setGenerationId(request.getGenerationId());
        } else if (generationProcess != null && generationProcess.getGenerationId() != null) {
            generation = generationService.getById(generationProcess.getGenerationId());
        }

        return convert(generation, generationProcess);
    }


    private Generation convert(GenerationCreateRequest request) {
        Generation generation = new Generation();
        generation.setName(request.getName());
        generation.setGangs(request.getGangs());
        for (Gang gang : generation.getGangs()) {
            gang.setGeneration(generation);
        }

        generation.setCreatedAt(Instant.now());
        return generation;
    }

    private GenerationResponse convert(Generation generation, GenerationProcess generationProcess) {
        if (generation == null) {
            generation = new Generation();
        }

        List<GangResponse> gangs = generation.getGangs().stream()
                .map(gang ->
                        GangResponse.builder()
                                .id(gang.getId())
                                .gangsters(gang.getGangsters())
                                .build())
                .sorted(Comparator.comparing((GangResponse gangResponse) -> gangResponse.getGangsters().size()))
                .collect(Collectors.toList());

        GenerationProcessResponse generationProcessResponse = null;
        if (generationProcess != null) {
            generationProcessResponse = convert(generationProcess);
        }

        return GenerationResponse.builder()
                .id(generation.getId())
                .name(generation.getName())
                .timeOfRequest(Instant.now())
                .gangs(gangs)
                .process(generationProcessResponse)
                .build();
    }

    private GenerationProcessResponse convert(GenerationProcess generationProcess) {
        return GenerationProcessResponse.builder()
                .id(generationProcess.getId())
                .createdAt(generationProcess.getCreatedAt())
                .duration(generationProcess.getDuration())
                .generationId(generationProcess.getGenerationId())
                .build();
    }
}

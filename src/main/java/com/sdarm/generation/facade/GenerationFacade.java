package com.sdarm.generation.facade;

import com.sdarm.generation.domain.Algorithm;
import com.sdarm.generation.domain.Gang;
import com.sdarm.generation.domain.Generation;
import com.sdarm.generation.domain.GenerationProcess;
import com.sdarm.generation.dto.GangResponse;
import com.sdarm.generation.dto.GenerationCreateRequest;
import com.sdarm.generation.dto.GenerationResponse;
import com.sdarm.generation.dto.GenerationProcessResponse;
import com.sdarm.generation.repository.GenerationProcessRepository;
import com.sdarm.generation.service.GenerationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
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
        if (id == null || id == 0) {
            generation = generationService.generate(Algorithm.GENDER_IS_THE_SAME);
        } else {
            generation = generationService.getById(id);
        }
        return convert(generation, null);
    }

    public GenerationResponse getLatest() {
        Generation generation = generationService.getLatest();
        return convert(generation, null);
    }

    public GenerationResponse create(GenerationCreateRequest request) {
        Generation generation = convert(request);
        generation = generationService.save(generation);
        log.info("Generation was successfully created in DB with id: {}", generation.getId());

        return convert(generation, null);
    }

    public GenerationResponse generate(Long duration, Algorithm algorithm, boolean prepared) {
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
                .createdAt(generationProcess.getCreatedAt())
                .duration(generationProcess.getDuration())
                .build();
    }
}

package com.sdarm.generation.facade;

import com.sdarm.generation.domain.Algorithm;
import com.sdarm.generation.domain.Gang;
import com.sdarm.generation.domain.Generation;
import com.sdarm.generation.dto.GangResponse;
import com.sdarm.generation.dto.GenerationCreateRequest;
import com.sdarm.generation.dto.GenerationResponse;
import com.sdarm.generation.service.GenerationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenerationFacade {

    private final GenerationService generationService;

    public List<GenerationResponse> getAll() {
        return generationService.getAll().stream()
                .map(generation -> GenerationResponse.builder()
                        .id(generation.getId())
                        .name(generation.getName())
                        .timeOfRequest(Instant.now())
                        .build())
                .collect(Collectors.toList());
    }

    public GenerationResponse getById(Long id) {
        Generation generation;
        if (id == null || id == 0) {
            generation = generationService.generate(Algorithm.GENDER_IS_THE_SAME);
        } else {
            generation = generationService.getById(id);
        }
        return convert(generation);
    }

    public GenerationResponse create(GenerationCreateRequest request) {

        Generation generation = new Generation();
        generation.setName(request.getName());
        generation.setGangs(request.getGangs());
        for (Gang gang : generation.getGangs()) {
            gang.setGeneration(generation);
        }

        generation = generationService.save(generation);
        log.info("Generation was successfully created in DB with id: {}", generation.getId());

        return convert(generation);
    }

    private GenerationResponse convert(Generation generation) {
        List<GangResponse> gangs = generation.getGangs().stream()
                .map(gang ->
                        GangResponse.builder()
                                .id(gang.getId())
                                .gangsters(gang.getGangsters())
                                .build())
                .sorted(Comparator.comparing((GangResponse gangResponse) -> gangResponse.getGangsters().size()))
                .collect(Collectors.toList());

        return GenerationResponse.builder()
                .id(generation.getId())
                .name(generation.getName())
                .timeOfRequest(Instant.now())
                .gangs(gangs)
                .build();
    }
}

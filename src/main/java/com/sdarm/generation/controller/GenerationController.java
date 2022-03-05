package com.sdarm.generation.controller;

import com.sdarm.generation.domain.Algorithm;
import com.sdarm.generation.dto.GenerationCreateRequest;
import com.sdarm.generation.dto.GenerationResponse;
import com.sdarm.generation.facade.GenerationFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("api/v1/generations")
@RequiredArgsConstructor
public class GenerationController {

    private final GenerationFacade generationFacade;

    @GetMapping
    public List<GenerationResponse> getAll() {
        log.info("Request for getting all generations");
        return generationFacade.getAll();
    }

    @GetMapping("latest")
    public GenerationResponse get() {
        log.info("Request for getting the latest generation");
        return generationFacade.getLatest();
    }

    @GetMapping("{id}")
    public GenerationResponse get(@PathVariable Long id) {
        log.info("Request for getting generation with id {}", id);
        return generationFacade.getById(id);
    }

    @PostMapping
    public GenerationResponse create(@RequestBody GenerationCreateRequest generationCreateRequest) {
        log.info("Request for creating generation");
        return generationFacade.create(generationCreateRequest);
    }

    @PostMapping("start")
    public GenerationResponse startProcess(@RequestParam(required = false) String name,
                                           @RequestParam(required = false, defaultValue = "900") long duration,
                                           @RequestParam(required = false, defaultValue = "false") boolean prepared,
                                           @RequestParam(required = false, defaultValue = "GENDER_IS_THE_SAME") Algorithm algorithm
    ) {
        log.info("Request for creating process of generation with duration {}", duration);
        return generationFacade.generate(name, duration, algorithm, prepared);
    }

    @PostMapping({"{id}/finish", "{id}/finish/{processId}"})
    public GenerationResponse finishProcess(@PathVariable("id") Long generationId,
                                            @PathVariable(required = false) Long processId) {
        log.info("Request for finalization of generation process");
        return generationFacade.finish(processId, generationId);
    }
}

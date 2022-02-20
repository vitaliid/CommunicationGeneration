package com.sdarm.generation.controller;

import com.sdarm.generation.dto.GenerationCreateRequest;
import com.sdarm.generation.facade.GenerationFacade;
import com.sdarm.generation.dto.GenerationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

}

package com.sdarm.generation.controller;

import com.sdarm.generation.dto.GenerationCreateRequest;
import com.sdarm.generation.service.GenerationFacade;
import com.sdarm.generation.dto.GenerationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/generations")
@RequiredArgsConstructor
public class GenerationController {

    private final GenerationFacade generationFacade;

    @GetMapping
    public List<GenerationResponse> get() {
        return generationFacade.getAll();
    }

    @GetMapping("{id}")
    public GenerationResponse getById(@PathVariable Long id) {
        return generationFacade.getById(id);
    }

    @PostMapping
    public GenerationResponse create(@RequestBody GenerationCreateRequest generationCreateRequest) {
        return generationFacade.create(generationCreateRequest);
    }

}

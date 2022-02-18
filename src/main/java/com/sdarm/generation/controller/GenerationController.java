package com.sdarm.generation.controller;

import com.sdarm.generation.GenerationFacade;
import com.sdarm.generation.dto.GenerationResponse;
import com.sdarm.generation.service.GenerationService;
import com.sdarm.generation.domain.Generation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}

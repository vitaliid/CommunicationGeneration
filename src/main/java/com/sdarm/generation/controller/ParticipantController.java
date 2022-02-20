package com.sdarm.generation.controller;

import com.sdarm.generation.dto.ParticipantResponse;
import com.sdarm.generation.facade.ParticipantFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/participants")
@RequiredArgsConstructor
public class ParticipantController {

    private final ParticipantFacade participantFacade;

    @GetMapping
    public List<ParticipantResponse> getAll() {
        log.info("Request for getting all participants");
        return participantFacade.getAll();
    }

}

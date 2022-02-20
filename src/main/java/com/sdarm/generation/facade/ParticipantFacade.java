package com.sdarm.generation.facade;

import com.sdarm.generation.domain.Participant;
import com.sdarm.generation.dto.ParticipantResponse;
import com.sdarm.generation.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParticipantFacade {

    private final ParticipantService participantService;

    public List<ParticipantResponse> getAll() {
        return participantService.getAll().stream()
                .map(this::convert)
                .sorted(Comparator.comparing(ParticipantResponse::getId))
                .collect(Collectors.toList());
    }

    private ParticipantResponse convert(Participant participant) {
        return ParticipantResponse.builder()
                .id(participant.getId())
                .name(participant.getName())
                .gender(participant.getGender())
                .build();
    }
}

package com.sdarm.generation.service.impl;

import com.sdarm.generation.domain.Participant;
import com.sdarm.generation.repository.ParticipantRepository;
import com.sdarm.generation.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParticipantServiceImpl implements ParticipantService {

    private final ParticipantRepository participantRepository;

    public List<Participant> getAll() {
        return participantRepository.findAll();
    }
}

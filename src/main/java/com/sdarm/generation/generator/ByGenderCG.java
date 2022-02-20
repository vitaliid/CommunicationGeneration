package com.sdarm.generation.generator;

import com.sdarm.generation.domain.Gang;
import com.sdarm.generation.domain.Gender;
import com.sdarm.generation.domain.Generation;
import com.sdarm.generation.domain.Participant;
import com.sdarm.generation.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ByGenderCG implements CommunicationGenerator {

    private final ParticipantRepository participantRepository;

    @Override
    public Generation generate() {
        List<Participant> all = participantRepository.findAll();

        Generation generation = new Generation();
        generation.setId(0L);
        generation.setName("Test generation");

        List<Participant> males = all.stream().filter(participant -> participant.getGender() == Gender.MALE).collect(Collectors.toList());
        List<Participant> females = all.stream().filter(participant -> participant.getGender() == Gender.FEMALE).collect(Collectors.toList());

        Collections.shuffle(males);
        Collections.shuffle(females);

        generation.getGangs().addAll(createGangs(males));
        generation.getGangs().addAll(createGangs(females));

        for (Gang gang : generation.getGangs()) {
            gang.setGeneration(generation);
        }

        generation.setCreatedAt(Instant.now());
        return generation;
    }

    private List<Gang> createGangs(List<Participant> participants) {
        List<Gang> gangs = new ArrayList<>();

        Gang prevGang = null;
        for (int i = 0; i <= (participants.size() - 1) / 2; i++) {
            Gang gang = new Gang();


            final int leftGangster = i;
            final int rightGangster = participants.size() - 1 - i;

            if (leftGangster == rightGangster && prevGang != null) {
                prevGang.getGangsters().add(participants.get(leftGangster));
                continue;
            }

            gang.getGangsters().add(participants.get(leftGangster));
            gang.getGangsters().add(participants.get(rightGangster));

            prevGang = gang;
            gangs.add(gang);
        }

        return gangs;
    }
}

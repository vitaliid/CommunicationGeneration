package com.sdarm.generation.generator.algo;

import com.sdarm.generation.domain.Gang;
import com.sdarm.generation.domain.Gender;
import com.sdarm.generation.domain.Generation;
import com.sdarm.generation.domain.Participant;
import com.sdarm.generation.generator.CommunicationGenerator;
import com.sdarm.generation.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class WithOppositeGender implements CommunicationGenerator {

    private final ParticipantRepository participantRepository;

    @Override
    public Generation generate() {
        List<Participant> all = participantRepository.findAll();

        Generation generation = new Generation();
        generation.setId(0L);
        generation.setName("Prepared generation " + UUID.randomUUID());

        List<Participant> males = all.stream().filter(participant -> participant.getGender() == Gender.MALE).collect(Collectors.toList());
        List<Participant> females = all.stream().filter(participant -> participant.getGender() == Gender.FEMALE).collect(Collectors.toList());

        Collections.shuffle(males);
        Collections.shuffle(females);

        for (int i = 0; i < Math.min(males.size(), females.size()); i++) {
            Gang gang = new Gang();
            gang.setGeneration(generation);
            gang.getGangsters().add(males.get(i));
            gang.getGangsters().add(females.get(i));
            generation.getGangs().add(gang);
        }

        /*
        if (males.size() < females.size()) {
            int difference = females.size() - males.size();
            int additionalGirlsPerGroup = (int) Math.ceil(difference / generation.getGangs().size());
            for (int i = 0; i < generation.getGangs().size(); i = i + additionalGirlsPerGroup) {

            }
        }*/

        generation.setCreatedAt(Instant.now());
        return generation;
    }
}

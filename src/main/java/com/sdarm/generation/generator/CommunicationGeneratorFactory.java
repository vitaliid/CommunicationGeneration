package com.sdarm.generation.generator;

import com.sdarm.generation.domain.Algorithm;
import com.sdarm.generation.generator.algo.WithOppositeGender;
import com.sdarm.generation.generator.algo.WithTheSameGender;
import com.sdarm.generation.repository.ParticipantRepository;
import org.springframework.stereotype.Component;

@Component
public class CommunicationGeneratorFactory {

    private final WithTheSameGender theSameGender;
    private final WithOppositeGender oppositeGender;

    public CommunicationGeneratorFactory(ParticipantRepository participantRepository) {
        this.theSameGender = new WithTheSameGender(participantRepository);
        this.oppositeGender = new WithOppositeGender(participantRepository);
    }

    public CommunicationGenerator getCommunicationGenerator(Algorithm algorithm) {
        if (algorithm == null) {
            algorithm = Algorithm.GENDER_IS_THE_SAME;
        }

        switch (algorithm) {
            case GENDER_IS_THE_SAME:
                return theSameGender;
            case GENDER_IS_OPPOSITE:
                return oppositeGender;
        }

        throw new IllegalStateException("Unexpected value for Algorithm: " + algorithm);
    }
}

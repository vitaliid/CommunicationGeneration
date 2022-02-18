package com.sdarm.generation.generator;

import com.sdarm.generation.domain.Algorithm;
import com.sdarm.generation.repository.ParticipantRepository;
import org.springframework.stereotype.Component;

@Component
public class CommunicationGeneratorFactory {

    private final ByGenderCG genderCG;

    public CommunicationGeneratorFactory(ParticipantRepository participantRepository) {
        this.genderCG = new ByGenderCG(participantRepository);
    }

    public CommunicationGenerator getCommunicationGenerator(Algorithm algorithm) {
        if (algorithm == null) {
            algorithm = Algorithm.GENDER_IS_THE_SAME;
        }

        if (algorithm == Algorithm.GENDER_IS_THE_SAME) {
            return genderCG;
        }
        throw new IllegalStateException("Unexpected value for Algorithm: " + algorithm);
    }
}

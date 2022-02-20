package com.sdarm.generation.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "gang")
@Getter
@Setter
public class Gang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "generation_id")
    private Generation generation;


    @ManyToMany(cascade = {
            // CascadeType.PERSIST,
            CascadeType.MERGE
    },
            fetch = FetchType.EAGER)
    @JoinTable(name = "gang_participant",
            joinColumns = @JoinColumn(name = "gang_id"),
            inverseJoinColumns = @JoinColumn(name = "participant_id")
    )
    @Fetch(FetchMode.SUBSELECT)
    private List<Participant> gangsters = new ArrayList<>();
}

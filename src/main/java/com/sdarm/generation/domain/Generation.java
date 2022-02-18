package com.sdarm.generation.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "generation")
@Getter
@Setter
public class Generation {

    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @Transient
    private List<Gang> gangs = new ArrayList<>();
}

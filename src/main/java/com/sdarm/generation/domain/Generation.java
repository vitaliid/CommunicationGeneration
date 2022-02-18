package com.sdarm.generation.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "generation")
@Getter
@Setter
public class Generation {

    @Id
    private Long id;

    @Column(name = "name")
    private String name;
}

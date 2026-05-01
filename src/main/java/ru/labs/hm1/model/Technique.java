package ru.labs.hm1.model;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "techniques")
@Data
public class Technique {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String type;
    private long damage;

    @ManyToOne
    @JoinColumn(name = "sorcerer_id")
    @JsonIgnore
    private Sorcerer sorcerer;

}
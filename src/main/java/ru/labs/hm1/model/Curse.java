package ru.labs.hm1.model;

import jakarta.persistence.*;
import lombok.Data;

@Embeddable
@Data
public class Curse {
    @Column(name = "curse_name")
    private String name;
    @Enumerated(EnumType.STRING)
    private ThreatLevel threatLevel;
}
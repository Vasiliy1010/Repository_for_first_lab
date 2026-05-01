package ru.labs.hm1.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sorcerers")
@Data
public class Sorcerer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @Enumerated(EnumType.STRING)
    private Rank rank;

    @OneToMany(mappedBy = "sorcerer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Technique> techniques = new ArrayList<>();

    public void setTechniques(List<Technique> techniques) {
        this.techniques = techniques;
        if (techniques != null) {
            for (Technique ttechnique : techniques) {
                ttechnique.setSorcerer(this);
            }
        }
    }

    public void addTechnique(Technique technique) {
        techniques.add(technique);
        technique.setSorcerer(this);
    }
}
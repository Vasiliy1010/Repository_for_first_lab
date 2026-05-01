package ru.labs.hm1.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import jakarta.persistence.*;
import lombok.Data;
import java.util.*;

@Entity
@Table(name = "missions")
@Data
public class Mission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String missionId;

    private String date;
    private String location;

    @Enumerated(EnumType.STRING)
    private Outcome outcome;

    @Embedded private Curse curse;
    @Embedded private EconomicAssessment economicAssessment;
    @Embedded private EnvironmentData environment;
    @Embedded private CivilianImpact civilianImpact;
    @Embedded private EnemyActivity enemyActivity;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "mission_sorcerers", joinColumns = @JoinColumn(name = "mission_id"), inverseJoinColumns = @JoinColumn(name = "sorcerer_id"))
    private List<Sorcerer> sorcerers = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "mission_id")
    private List<OperationTimeLine> operationTimeLine = new ArrayList<>();

    @ElementCollection
    private List<String> operationTags = new ArrayList<>();

    @ElementCollection
    private List<String> supportUnits = new ArrayList<>();

    @ElementCollection
    private List<String> recommendations = new ArrayList<>();

    @ElementCollection
    private List<String> artifactsRecovered = new ArrayList<>();

    @ElementCollection
    private List<String> evacuationZones = new ArrayList<>();

    @ElementCollection
    @MapKeyColumn(name = "effect_name")
    @Column(name = "effect_description")
    private Map<String, String> statusEffects = new HashMap<>();

    @Column(columnDefinition = "TEXT")
    private String notes;

    @JsonAnySetter
    public void addExtra(String key, Object value) {
        this.addField(key, value != null ? value.toString() : "null");
    }

    @JsonSetter("techniques")
    public void bindTechniques(List<Map<String, Object>> techRawData) {
        if (techRawData == null || this.sorcerers == null) return;

        for (Map<String, Object> data : techRawData) {
            String ownerName = (String) data.get("owner");

            this.sorcerers.stream().filter(s -> s.getName().equalsIgnoreCase(ownerName))
                    .findFirst()
                    .ifPresent(sorcerer -> {
                        Technique tech = new Technique();
                        tech.setName((String) data.get("name"));
                        tech.setType((String) data.get("type"));
                        Object damage = data.get("damage");
                        tech.setDamage(damage instanceof Number ? ((Number) damage).longValue() : 0L);
                        sorcerer.addTechnique(tech);
                    });
        }
    }

    @JsonAnyGetter
    public Map<String, Object> getExtra() {
        return Collections.emptyMap();
    }

    public void addField(String key, String value) {
        if (this.notes == null) {
            this.notes = "";
        }
        if (!this.notes.isEmpty()) {
            this.notes += " | ";
        }
        this.notes += key + ": " + value;
    }
}
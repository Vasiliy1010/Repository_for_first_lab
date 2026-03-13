package ru.labs.hm1.mission;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mission {
    private String missionId;
    private String date;
    private String location;
    private String outcome;
    private long damageCost;
    private Curse curse;
    private List<Sorcerer> sorcerers = new ArrayList<>();
    private List<Technique> techniques = new ArrayList<>();
    private Map<String, Object> otherDate = new HashMap<>();

    public String getMissionId() {
        return missionId;
    }

    public void setMissionId(String missionId) {
        this.missionId = missionId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public long getDamageCost() {
        return damageCost;
    }

    public void setDamageCost(long damageCost) {
        this.damageCost = damageCost;
    }

    public Curse getCurse() {
        return curse;
    }

    public void setCurse(Curse curse) {
        this.curse = curse;
    }

    public List<Sorcerer> getSorcerers() {
        return sorcerers;
    }

    public void setSorcerers(List<Sorcerer> sorcerers) {
        this.sorcerers = sorcerers;
    }

    public List<Technique> getTechniques() {
        return techniques;
    }

    public void setTechniques(List<Technique> techniques) {
        this.techniques = techniques;
    }

    @JsonAnyGetter
    public Map<String, Object> getExtraFields() {
        return otherDate;
    }

    @JsonAnySetter
    public void addField(String name, Object value) {
        this.otherDate.put(name, value);
    }

    public void displaySummary() {
        System.out.println("\nОтчет по миссии");
        for (Sorcerer s: sorcerers){
            System.out.println("Имя мага: " + s.getName() + " Ранг мага: " + s.getRank());
        }
        System.out.println("Название проклятья: " + curse.getName() + " Уровень проклятья: " + curse.getThreatLevel());
        for (Technique t: techniques){
            System.out.println("Название техники мага: " + t.getName() + " Тип техники: " + t.getType() + " Имя владельца: " + t.getOwner() + " Наносимый урон: " + t.getDamage());
        }
        System.out.println("Результат миссии: " + outcome);
    }

}

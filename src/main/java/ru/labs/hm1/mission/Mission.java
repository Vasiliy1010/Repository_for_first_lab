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
    private Curse curse;
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

    public Curse getCurse() {
        return curse;
    }

    public void setCurse(Curse curse) {
        this.curse = curse;
    }

    @JsonAnyGetter
    public Map<String, Object> getExtraFields() {
        return otherDate;
    }

    @JsonAnySetter
    public void addField(String name, Object value) {
        this.otherDate.put(name, value);
    }
}

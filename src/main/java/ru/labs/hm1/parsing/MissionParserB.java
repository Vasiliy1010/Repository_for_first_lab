package ru.labs.hm1.parsing;

import ru.labs.hm1.model.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MissionParserB implements MissionParser {

    @Override
    public Mission loadMission(String filePath) throws IOException {
        Mission mission = new Mission();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()){
                    continue;
                }
                int separator = line.indexOf('|');
                if (separator == -1){
                    continue;
                }
                String key = line.substring(0, separator).trim();
                String rawData = line.substring(separator + 1).trim();
                String[] values = rawData.split("\\|");
                mapMission(mission, key, values);
            }
        }
        return mission;
    }

    private void mapMission(Mission mission, String key, String[] values) {
        if (values == null || values.length == 0) return;

        switch (key.toUpperCase()) {
            case "MISSION_CREATED":
                mission.setMissionId(values[0]);
                if (values.length > 2) mission.setLocation(values[2]);
                break;

            case "CURSE_DETECTED":
                Curse curse = new Curse();
                curse.setName(values[0]);
                if (values.length > 1) curse.setThreatLevel(ThreatLevel.valueOf(values[1].toUpperCase()));
                mission.setCurse(curse);
                break;

            case "SORCERER_ASSIGNED":
                Sorcerer sorcerer = new Sorcerer();
                sorcerer.setName(values[0]);
                sorcerer.setTechniques(new ArrayList<>());
                if (values.length > 1) {
                    try {
                        sorcerer.setRank(Rank.valueOf(values[1].toUpperCase()));
                    } catch (Exception e) {
                        sorcerer.setRank(Rank.UNKNOWN);
                    }
                }
                mission.getSorcerers().add(sorcerer);
                break;

            case "TECHNIQUE_USED":
                if (values.length >= 3) {
                    String techName = values[0];
                    String techType = values[1];
                    String ownerName = values[2];
                    long damage = values.length > 3 ? Long.parseLong(values[3]) : 0L;

                    boolean found = false;
                    for (Sorcerer s : mission.getSorcerers()) {
                        if (s.getName().equalsIgnoreCase(ownerName)) {
                            Technique tech = new Technique();
                            tech.setName(techName);
                            tech.setType(techType);
                            tech.setDamage(damage);
                            s.addTechnique(tech);
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        String warning = "ВНИМАНИЕ: Маг " + ownerName + " не найден для техники " + techName;
                        mission.setNotes(mission.getNotes() == null ? warning : mission.getNotes() + " | " + warning);
                    }
                }
                break;

            case "MISSION_RESULT":
                try {
                    mission.setOutcome(Outcome.valueOf(values[0].toUpperCase()));
                } catch (Exception e) {
                    mission.setOutcome(Outcome.UNKNOWN);
                }
                break;

            case "TAGS":
                for (String v : values) mission.getOperationTags().add(v.trim());
                break;

            case "SUPPORT":
                for (String v : values) mission.getSupportUnits().add(v.trim());
                break;

            case "RECOMMENDATION":
                for (String v : values) mission.getRecommendations().add(v.trim());
                break;

            case "STATUS_EFFECT":
                if (values.length >= 2) {
                    mission.getStatusEffects().put(values[0].trim(), values[1].trim());
                }
                break;

            default:
                String unknownInfo = key + ": " + String.join(", ", values);
                if (mission.getNotes() == null) {
                    mission.setNotes(unknownInfo);
                } else {
                    mission.setNotes(mission.getNotes() + " | " + unknownInfo);
                }
                break;
        }
    }
}
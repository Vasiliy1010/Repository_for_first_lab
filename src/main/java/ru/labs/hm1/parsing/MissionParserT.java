package ru.labs.hm1.parsing;

import ru.labs.hm1.model.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class MissionParserT implements MissionParser {

    @Override
    public Mission loadMission(String filePath) throws IOException {
        Mission mission = new Mission();
        mission.setSorcerers(new ArrayList<>());
        mission.setOperationTimeLine(new ArrayList<>());

        String currentSection = "";
        Sorcerer currentSorcerer = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith(";")) continue;

                if (line.startsWith("[") && line.endsWith("]")) {
                    currentSection = line.substring(1, line.length() - 1).toUpperCase();
                    if (currentSection.equals("SORCERER")) {
                        currentSorcerer = new Sorcerer();
                        mission.getSorcerers().add(currentSorcerer);
                    }
                    continue;
                }

                int separator = line.indexOf('=');
                if (separator == -1) continue;

                String key = line.substring(0, separator).trim();
                String value = line.substring(separator + 1).trim();

                switch (currentSection) {
                    case "MISSION":
                        mapMission(mission, key, value);
                        break;
                    case "CURSE":
                        mapCurse(mission, key, value);
                        break;
                    case "SORCERER":
                        if (currentSorcerer != null) {
                            mapSorcerer(currentSorcerer, key, value);
                        }
                        break;
                    case "TECHNIQUE":
                        mapTechnique(mission, key, value);
                        break;
                    case "EVENT":
                        OperationTimeLine event = new OperationTimeLine();
                        event.setTimestamp(event.getTimestamp());
                        event.setEventInfo(value);
                        mission.getOperationTimeLine().add(event);
                        break;
                }
            }
        }
        return mission;
    }

    private void mapMission(Mission mission, String key, String value) {
        switch (key.toLowerCase()) {
            case "missionid": mission.setMissionId(value); break;
            case "location": mission.setLocation(value); break;
            case "date": mission.setDate(value); break;
            case "outcome":
                try {
                    mission.setOutcome(Outcome.valueOf(value.toUpperCase()));
                } catch (Exception e) {
                    mission.setOutcome(Outcome.UNKNOWN);
                }
                break;
        }
    }

    private void mapCurse(Mission mission, String key, String value) {
        if (mission.getCurse() == null) mission.setCurse(new Curse());
        if (key.equalsIgnoreCase("name")) mission.getCurse().setName(value);
        if (key.equalsIgnoreCase("threatlevel")) {
            try {
                mission.getCurse().setThreatLevel(ThreatLevel.valueOf(value.toUpperCase()));
            } catch (Exception e) {
                mission.getCurse().setThreatLevel(ThreatLevel.UNKNOWN);
            }
        }
    }

    private void mapSorcerer(Sorcerer sorcerer, String key, String value) {
        switch (key.toLowerCase()) {
            case "name": sorcerer.setName(value); break;
            case "rank":
                try {
                    sorcerer.setRank(Rank.valueOf(value.toUpperCase()));
                } catch (Exception e) {
                    sorcerer.setRank(Rank.UNKNOWN);
                }
                break;
        }
    }

    private void mapTechnique(Mission mission, String key, String value) {
        String[] parts = value.split("\\|");
        mission.getSorcerers().stream()
                .filter(s -> s.getName() != null && s.getName().equalsIgnoreCase(key))
                .findFirst()
                .ifPresent(s -> {
                    Technique t = new Technique();
                    t.setName(parts[0].trim());
                    if (parts.length > 1) t.setType(parts[1].trim());
                    if (parts.length > 2) t.setDamage(Long.parseLong(parts[2].trim()));
                    s.addTechnique(t);
                });
    }
}
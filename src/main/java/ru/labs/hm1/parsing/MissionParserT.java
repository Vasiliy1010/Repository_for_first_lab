package ru.labs.hm1.parsing;

import ru.labs.hm1.mission.Curse;
import ru.labs.hm1.mission.Mission;
import ru.labs.hm1.mission.Sorcerer;
import ru.labs.hm1.mission.Technique;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MissionParserT implements MissionParser {
    @Override
    public Mission loadMission(String filePath) throws IOException {
        Mission mission = new Mission();
        String currentSection = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()){
                    continue;
                }
                if (line.startsWith("[") && line.endsWith("]")) {
                    currentSection = line.substring(1, line.length() - 1);
                    continue;
                }
                int separator = line.indexOf('=');
                if (separator == -1){
                    continue;
                }
                String key = line.substring(0, separator).trim();
                String value = line.substring(separator + 1).trim();
                switch (currentSection) {
                    case "MISSION":
                        mapMission(mission, key, value);
                        break;
                    case "CURSE":
                        if (mission.getCurse() == null) {
                            Curse curse = new Curse();
                            mission.setCurse(curse);
                        }
                        mapCurse(mission.getCurse(), key, value);
                        break;
                    default:
                        mapExtra(mission, currentSection, key, value);
                        break;
                }
            }
        }
        return mission;
    }

    private void mapMission(Mission mission, String key, String value) {
        switch (key) {
            case "missionId":
                mission.setMissionId(value);
                break;
            case "date":
                mission.setDate(value);
                break;
            case "location":
                mission.setLocation(value);
                break;
            case "outcome":
                mission.setOutcome(value);
                break;
        }
    }

    private void mapCurse(Curse curse, String key, String value) {
        if (curse == null) return;
        switch (key) {
            case "name":
                curse.setName(value);
                break;
            case "threatLevel":
                curse.setThreatLevel(value);
                break;
        }
    }

    private void mapExtra(Mission mission, String sectionName, String key, String value) {
        Map<String, Object> extraFields = mission.getExtraFields();
        Object sectionData = extraFields.get(sectionName);
        if (!(sectionData instanceof Map)) {
            sectionData = new HashMap<String, Object>();
            extraFields.put(sectionName, sectionData);
        }
        Map<String, Object> map = (Map<String, Object>) sectionData;
        if (map.containsKey(key)) {
            Object existing = map.get(key);
            if (existing instanceof List) {
                ((List<Object>) existing).add(value);
            } else {
                List<Object> list = new ArrayList<>();
                list.add(existing);
                list.add(value);
                map.put(key, list);
            }
        } else {
            map.put(key, value);
        }
    }
}
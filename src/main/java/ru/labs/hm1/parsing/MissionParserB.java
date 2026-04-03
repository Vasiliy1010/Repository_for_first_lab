package ru.labs.hm1.parsing;

import ru.labs.hm1.mission.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        switch (key) {
            case "MISSION_CREATED":
                mission.setMissionId(values[0]);
                mission.setDate(values[1]);
                mission.setLocation(values[2]);
                break;

            case "CURSE_DETECTED":
                Curse curse = new Curse();
                curse.setName(values[0]);
                curse.setThreatLevel(values[1]);
                mission.setCurse(curse);
                break;

            case "MISSION_RESULT":
                mission.setOutcome(values[0]);
                break;

            default:
                String newValue = String.join("|", values);
                Map<String, Object> otherData = mission.getExtraFields();
                if (otherData.containsKey(key)) {
                    Object existing = otherData.get(key);
                    if (existing instanceof List) {
                        ((List<Object>) existing).add(newValue);
                    } else {
                        List<Object> list = new ArrayList<>();
                        list.add(existing);
                        list.add(newValue);
                        otherData.put(key, list);
                    }
                } else {
                    mission.addField(key, newValue);
                }
                break;
        }
    }
}
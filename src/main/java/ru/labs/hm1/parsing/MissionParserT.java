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

public class MissionParserT extends MissionParser {

    @Override
    public Mission loadMission(String filePath) throws IOException {
        existFile(filePath);
        Mission mission = new Mission();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                int separator = line.indexOf(':');
                String key = line.substring(0, separator).trim();
                String value = line.substring(separator + 1).trim();
                mapFieldMission(mission, key, value);
            }
        }
        setObjects(mission);
        return mission;
    }

    private void mapFieldMission(Mission mission, String key, String value) {
        switch (key) {
            case "missionId":
                mission.setMissionId(value);
                return;
            case "date":
                mission.setDate(value);
                return;
            case "location":
                mission.setLocation(value);
                return;
            case "outcome":
                mission.setOutcome(value);
                return;
            case "damageCost":
                long val = Long.parseLong(value);
                mission.setDamageCost(val);
                return;
        }
        if (key.contains(".") || key.contains("[")) {
            processRead(mission, key, value);
        }
        else {
            mission.addField(key, value);
        }
    }

    private void processRead(Mission mission, String key, String value) {
        Map<String, Object> map = mission.getExtraFields();

        if (key.contains("[") && key.contains(".")) {
            String arrayName = key.substring(0, key.indexOf('['));
            int index = Integer.parseInt(key.substring(key.indexOf('[') + 1, key.indexOf(']')));
            String fieldName = key.substring(key.indexOf('.') + 1);
            List<Map<String, Object>> list = (List<Map<String, Object>>) map.get(arrayName);
            if (list == null) {
                list = new ArrayList<>();
                map.put(arrayName, list);
            }
            while (list.size() <= index) {
                list.add(new HashMap<>());
            }
            list.get(index).put(fieldName, value);
        }
        else if (key.contains(".")) {
            String name = key.substring(0, key.indexOf('.'));
            String fieldName = key.substring(key.indexOf('.') + 1);
            Map<String, Object> mapCurse = (Map<String, Object>) map.get(name);
            if (mapCurse == null) {
                mapCurse = new HashMap<>();
                map.put(name, mapCurse);
            }
            mapCurse.put(fieldName, value);
        }
    }

    private void setObjects(Mission mission) {
        Map<String, Object> saveMap = mission.getExtraFields();

        if (saveMap.containsKey("curse")) {
            Map<String, String> curseData = (Map<String, String>) saveMap.get("curse");
            Curse curse = new Curse();
            curse.setName(curseData.get("name"));
            curse.setThreatLevel(curseData.get("threatLevel"));
            mission.setCurse(curse);
            saveMap.remove("curse");
        }

        if (saveMap.containsKey("sorcerer")) {
            List<Map<String, String>> sorcererList = (List<Map<String, String>>) saveMap.get("sorcerer");
            List<Sorcerer> sorcerers = new ArrayList<>();
            for (Map<String, String> sorcererData : sorcererList) {
                Sorcerer sorcerer = new Sorcerer();
                sorcerer.setName(sorcererData.get("name"));
                sorcerer.setRank(sorcererData.get("rank"));
                sorcerers.add(sorcerer);
            }
            mission.setSorcerers(sorcerers);
            saveMap.remove("sorcerer");
        }

        if (saveMap.containsKey("technique")) {
            List<Map<String, String>> techList = (List<Map<String, String>>) saveMap.get("technique");
            List<Technique> techniques = new ArrayList<>();
            for (Map<String, String> techData : techList) {
                Technique tech = new Technique();
                tech.setName(techData.get("name"));
                tech.setType(techData.get("type"));
                tech.setOwner(techData.get("owner"));
                tech.setDamage(Long.parseLong(techData.get("damage")));
                techniques.add(tech);
            }
            mission.setTechniques(techniques);
            saveMap.remove("technique");
        }
    }
}

package ru.labs.hm1.save;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ru.labs.hm1.mission.Mission;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MissionSaverJ implements MissionSaverMain{
    private static ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    @Override
    public void save(Mission newMission, String fileName) throws IOException {
        File file = new File(fileName);
        List<Mission> allMissions = new ArrayList<>();
        allMissions = objectMapper.readValue(file, new TypeReference<List<Mission>>() {});
        allMissions.add(newMission);
        objectMapper.writeValue(file, allMissions);
        System.out.println("Миссия добавлена");
    }
}

package ru.labs.hm1.parsing;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.labs.hm1.mission.Mission;

import java.io.File;
import java.io.IOException;

public class MissionParserJ extends MissionParser {
    private ObjectMapper objectMapper;

    public MissionParserJ(){
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Mission loadMission(String filePath) throws IOException {
        existFile(filePath);
        File file = new File(filePath);
        return objectMapper.readValue(file, Mission.class);
    }
}

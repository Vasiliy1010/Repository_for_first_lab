package ru.labs.hm1.parsing;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import ru.labs.hm1.mission.Mission;

import java.io.File;
import java.io.IOException;

public class MissionParserY implements MissionParser {
    private ObjectMapper yamlMapper;

    public MissionParserY(){
        this.yamlMapper = new ObjectMapper(new YAMLFactory());
        this.yamlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public Mission loadMission(String filePath) throws IOException {
        return yamlMapper.readValue(new File(filePath), Mission.class);
    }
}

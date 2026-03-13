package ru.labs.hm1;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ru.labs.hm1.mission.Mission;

import java.io.File;
import java.io.IOException;

public class MissionSaver {
    private ObjectMapper objectMapper;

    public MissionSaver(){
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public void saveToJson(Mission mission, String fileName) throws IOException {
        File file = new File(fileName);
        objectMapper.writeValue(file, mission);
        System.out.println("Файл успешно сохранен: " + fileName);
    }
}

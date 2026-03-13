package ru.labs.hm1;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ru.labs.hm1.mission.Mission;

import java.io.File;
import java.io.IOException;

public class MissionSaver {
    private static ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    public static void saveToJson(Mission mission, String fileName) throws IOException {
        File file = new File(fileName);
        objectMapper.writeValue(file, mission);
        System.out.println("Файл успешно сохранен: " + fileName);
    }
}

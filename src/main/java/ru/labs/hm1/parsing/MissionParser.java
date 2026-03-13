package ru.labs.hm1.parsing;

import ru.labs.hm1.mission.Mission;

import java.io.File;
import java.io.IOException;

public abstract class MissionParser {
    public abstract Mission loadMission(String filePath) throws IOException;

    protected void existFile(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            throw new IOException("Файл не найден ");
        }
    }
}

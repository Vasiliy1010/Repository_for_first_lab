package ru.labs.hm1.parsing;

import ru.labs.hm1.mission.Mission;
import java.io.IOException;

public interface MissionParser {
    Mission loadMission(String filePath) throws IOException;
}

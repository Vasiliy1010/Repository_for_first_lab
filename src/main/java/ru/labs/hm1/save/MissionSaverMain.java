package ru.labs.hm1.save;

import ru.labs.hm1.mission.Mission;
import java.io.IOException;

public interface MissionSaverMain {
    void save(Mission mission, String filePath) throws IOException;
}

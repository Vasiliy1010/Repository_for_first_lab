package ru.labs.hm1;

import ru.labs.hm1.parsing.*;
import java.io.File;

public class ParserFactory {
    public static MissionParser getParser(String filePath) throws Exception {
        String lowerPath = filePath.toLowerCase();
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            throw new Exception();
        }
        if (lowerPath.endsWith(".json")) {
            return new MissionParserJ();
        }
        else if (lowerPath.endsWith(".xml")) {
            return new MissionParserX();
        }
        else if (lowerPath.endsWith(".txt")) {
            return new MissionParserT();
        }
        else if (lowerPath.endsWith(".yaml")) {
            return new MissionParserY();
        }
        else {
            return new MissionParserB();
        }
    }
}

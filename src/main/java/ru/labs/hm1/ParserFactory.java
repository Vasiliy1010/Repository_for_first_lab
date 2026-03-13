package ru.labs.hm1;

import ru.labs.hm1.parsing.MissionParser;
import ru.labs.hm1.parsing.MissionParserJ;
import ru.labs.hm1.parsing.MissionParserT;
import ru.labs.hm1.parsing.MissionParserX;

public class ParserFactory {
    public static MissionParser getParser(String filePath) throws Exception {
        String lowerPath = filePath.toLowerCase();

        if (lowerPath.endsWith(".json")) {
            return new MissionParserJ();
        }
        else if (lowerPath.endsWith(".xml")) {
            return new MissionParserX();
        }
        else if (lowerPath.endsWith(".txt")) {
            return new MissionParserT();
        }
        else {
            throw new Exception();
        }
    }
}

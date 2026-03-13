package ru.labs.hm1.parsing;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import ru.labs.hm1.mission.Mission;

import java.io.File;
import java.io.IOException;


public class MissionParserX extends MissionParser {
    private XmlMapper xmlMapper;

    public MissionParserX(){
        this.xmlMapper = new XmlMapper();
    }

    @Override
    public Mission loadMission(String filePath) throws IOException {
        existFile(filePath);
        File file = new File(filePath);
        return xmlMapper.readValue(file, Mission.class);
    }
}

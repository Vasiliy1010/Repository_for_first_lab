package ru.labs.hm1.output;

import ru.labs.hm1.model.*;

public class ConcreteSummary implements InterfaceSummary {
    private final Mission mission;

    public ConcreteSummary(Mission mission){
        this.mission = mission;
    }

    @Override
    public String getSummary() {
        return "ОТЧЕТ ПО МИССИИ\n" + "ID Миссии: " + mission.getMissionId() + "\n" +
                "Локация: " + mission.getLocation() + "\n" +
                (mission.getCurse() != null ? "Проклятие: " + mission.getCurse().getName() + "\n" : "") +
                "Результат: " + mission.getOutcome() + "\n";
    }
}
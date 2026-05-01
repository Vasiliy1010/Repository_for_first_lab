package ru.labs.hm1.model;

import org.springframework.stereotype.Component;
import ru.labs.hm1.output.ConcreteSummary;
import ru.labs.hm1.output.InterfaceSummary;
import ru.labs.hm1.output.SorcererDecorator;
import ru.labs.hm1.output.TimelineDecorator;

@Component
public class MissionFactory {
    public void updateMission(Mission mission, String curseName, String sorcerersNames, String outcome) {
        if (mission.getCurse() != null) {
            mission.getCurse().setName(curseName);
        }
        if (sorcerersNames != null) {
            updateSorcerers(mission, sorcerersNames);
        }
        mission.setOutcome(Outcome.valueOf(outcome));
    }

    private void updateSorcerers(Mission mission, String sorcerersNames) {
        mission.getSorcerers().clear();
        String[] names = sorcerersNames.split("\\s*,\\s*");
        for (String name : names) {
            String trimmedName = name.trim();
            if (!trimmedName.isEmpty()) {
                Sorcerer s = new Sorcerer();
                s.setName(trimmedName);
                mission.getSorcerers().add(s);
            }
        }
    }

    public String getReport(Mission mission, String type) {
        InterfaceSummary report = new ConcreteSummary(mission);
        return workDecorators(report, mission, type).getSummary();
    }

    private InterfaceSummary workDecorators(InterfaceSummary report, Mission mission, String type) {
        switch (type.toLowerCase()) {
            case "large":
                report = new SorcererDecorator(report, mission);
                report = new TimelineDecorator(report, mission);
                break;
            case "medium":
                report = new SorcererDecorator(report, mission);
                break;
        }
        return report;
    }
}
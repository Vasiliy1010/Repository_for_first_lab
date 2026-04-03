package ru.labs.hm1.output;

import ru.labs.hm1.mission.*;

public class SummaryBase implements Summary {
    private Mission mission;

    public SummaryBase(Mission mission){
        this.mission = mission;
    }

    @Override
    public void getSummary() {
        System.out.println("\nОтчет по миссии");
        Curse curse = mission.getCurse();
        System.out.println("Mission: " + mission.getMissionId());
        System.out.println("Curse: " + curse.getName() + " threatLevel: " + curse.getThreatLevel());
        System.out.println("Outcome: " + mission.getOutcome());
    }
}

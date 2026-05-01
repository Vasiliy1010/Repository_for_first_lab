package ru.labs.hm1.output;

import ru.labs.hm1.model.Mission;
import ru.labs.hm1.model.Sorcerer;
import ru.labs.hm1.model.Technique;

public class SorcererDecorator extends BaseDecoratorSummary {
    public SorcererDecorator(InterfaceSummary summary, Mission mission) {
        super(summary, mission);
    }

    @Override
    public String getSummary() {
        StringBuilder sb = new StringBuilder(super.getSummary());
        sb.append("\nУчаствующие Маги и Техники:\n");

        if (mission.getSorcerers() == null || mission.getSorcerers().isEmpty()) {
            sb.append("Маги в отчете не указаны.\n");
        } else {
            for (Sorcerer s : mission.getSorcerers()) {
                sb.append("Маг: ").append(s.getName()).append("\n");
                for (Technique t : s.getTechniques()) {
                    sb.append("Техника: ").append(t.getName())
                            .append(" (Урон: ").append(t.getDamage()).append(")\n");
                }
            }
        }
        return sb.toString();
    }
}
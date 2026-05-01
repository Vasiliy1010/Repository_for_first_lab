package ru.labs.hm1.output;

import ru.labs.hm1.model.Mission;
import ru.labs.hm1.model.OperationTimeLine;

public class TimelineDecorator extends BaseDecoratorSummary {
    public TimelineDecorator(InterfaceSummary summary, Mission mission) {
        super(summary, mission);
    }

    @Override
    public String getSummary() {
        StringBuilder sb = new StringBuilder(super.getSummary());

        sb.append("\nХронология событий\n");

        if (mission.getOperationTimeLine() == null || mission.getOperationTimeLine().isEmpty()) {
            sb.append("Данные о ходе операции отсутствуют.\n");
        } else {
            for (OperationTimeLine event : mission.getOperationTimeLine()) {
                sb.append("[").append(event.getTimestamp()).append("] ").append(event.getEventInfo()).append("\n");
            }
        }
        return sb.toString();
    }
}
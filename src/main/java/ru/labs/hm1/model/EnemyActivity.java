package ru.labs.hm1.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Embeddable
public class EnemyActivity {
    private String behaviorType;
    private String targetPriority;
    private String mobility;
    private String escalationRisk;
    @ElementCollection
    private List<String> attackPatterns = new ArrayList<>();
    @ElementCollection
    private List<String> countermeasuresUsed = new ArrayList<>();

    public void addAttackPattern(String pattern) {
        this.attackPatterns.add(pattern);
    }

    public void addCountermeasure(String measure) {
        this.countermeasuresUsed.add(measure);
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();

        if (behaviorType != null && !behaviorType.isEmpty()) {
            string.append("Тип поведения: ").append(behaviorType).append("\n");
        }
        if (targetPriority != null && !targetPriority.isEmpty()) {
            string.append("Целевой приоритет: ").append(targetPriority).append("\n");
        }
        if (mobility != null && !mobility.isEmpty()) {
            string.append("Мобильность: ").append(mobility).append("\n");
        }
        if (escalationRisk != null && !escalationRisk.isEmpty()) {
            string.append("Риск эскалации: ").append(escalationRisk).append("\n");
        }
        if (!attackPatterns.isEmpty()) {
            string.append("Паттерны атак:\n");
            for (String pattern : attackPatterns) {
                string.append(" ").append(pattern).append("\n");
            }
        }
        if (!countermeasuresUsed.isEmpty()) {
            string.append("Использованные контрмеры:\n");
            for (String measure : countermeasuresUsed) {
                string.append(" ").append(measure).append("\n");
            }
        }
        return string.toString();
    }
}

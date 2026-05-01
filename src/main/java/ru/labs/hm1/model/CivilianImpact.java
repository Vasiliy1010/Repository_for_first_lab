package ru.labs.hm1.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class CivilianImpact {
    private int evacuated;
    private int injured;
    private int missing;
    private String publicExposureRisk;

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("Эвакуировано: ").append(evacuated).append("\n");
        string.append("Ранено: ").append(injured).append("\n");
        string.append("Пропало без вести: ").append(missing).append("\n");
        if (publicExposureRisk != null && !publicExposureRisk.isEmpty()) {
            string.append("Риск раскрытия: ").append(publicExposureRisk).append("\n");
        }
        return string.toString();
    }
}

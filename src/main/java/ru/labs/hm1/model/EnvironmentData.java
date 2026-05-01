package ru.labs.hm1.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class EnvironmentData {
    private String weather;
    private String timeOfDay;
    private String visibility;
    private int cursedEnergyDensity;

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        if (weather != null && !weather.isEmpty()) {
            string.append("Погода: ").append(weather).append("\n");
        }
        if (timeOfDay != null && !timeOfDay.isEmpty()) {
            string.append("Время суток: ").append(timeOfDay).append("\n");
        }
        if (visibility != null && !visibility.isEmpty()) {
            string.append("Видимость: ").append(visibility).append("\n");
        }
        if (cursedEnergyDensity > 0) {
            string.append("Плотность проклятой энергии: ").append(cursedEnergyDensity).append("%\n");
        }
        return string.toString();
    }
}

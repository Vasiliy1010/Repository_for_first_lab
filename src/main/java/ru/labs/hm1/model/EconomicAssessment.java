package ru.labs.hm1.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class EconomicAssessment {
    private int totalDamageCost;
    private int infrastructureDamage;
    private int transportDamage;
    private int commercialDamage;
    private int recoveryEstimateDays;
    private boolean insuranceCovered;

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();

        if (totalDamageCost > 0) {
            string.append("Общий ущерб: ").append(String.format("%d", totalDamageCost)).append("\n");
        }
        if (infrastructureDamage > 0) {
            string.append("Ущерб инфраструктуре: ").append(String.format("%d", infrastructureDamage)).append("\n");
        }
        if (transportDamage > 0) {
            string.append("Транспортный ущерб: ").append(String.format("%d", transportDamage)).append("\n");
        }
        if (commercialDamage > 0) {
            string.append("Коммерческий ущерб: ").append(String.format("%d", commercialDamage)).append("\n");
        }
        if (recoveryEstimateDays > 0) {
            string.append("Время восстановления: ").append(recoveryEstimateDays).append(" дней\n");
        }
        string.append("Страховое покрытие: ").append(insuranceCovered ? "Да" : "Нет").append("\n");

        return string.toString();
    }
}

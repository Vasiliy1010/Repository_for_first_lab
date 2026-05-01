package ru.labs.hm1.parsing;

import ru.labs.hm1.model.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MissionParserB implements MissionParser {

    @Override
    public Mission loadMission(String filePath) throws IOException {
        Mission mission = new Mission();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()){
                    continue;
                }
                int separator = line.indexOf('|');
                if (separator == -1){
                    continue;
                }
                String key = line.substring(0, separator).trim();
                String rawData = line.substring(separator + 1).trim();
                String[] values = rawData.split("\\|");

                // Обрабатываем каждую строку отдельно
                mapMission(mission, key, values);
            }
        }
        return mission;
    }

    private void mapMission(Mission mission, String key, String[] values) {
        // Если значений нет, просто выходим, чтобы не было ошибки
        if (values == null || values.length == 0) return;

        switch (key) {
            case "MISSION_CREATED":
                mission.setMissionId(values[0]);
                if (values.length > 2) mission.setLocation(values[2]);
                break;

            case "CURSE_DETECTED":
                Curse curse = new Curse();
                curse.setName(values[0]);
                if (values.length > 1) curse.setThreatLevel(ThreatLevel.valueOf(values[1].toUpperCase()));
                mission.setCurse(curse);
                break;

            case "SORCERER_ASSIGNED":
                Sorcerer sorcerer = new Sorcerer();
                sorcerer.setName(values[0]);
                // Безопасно парсим Enum
                if (values.length > 1) {
                    try {
                        sorcerer.setRank(Rank.valueOf(values[1].toUpperCase()));
                    } catch (Exception e) {
                        sorcerer.setRank(Rank.UNKNOWN);
                    }
                }
                mission.getSorcerers().add(sorcerer);
                break;

            case "TECHNIQUE_USED":
                // Формат: TECHNIQUE_USED | Имя мага | Название техники | Тип | Урон
                if (values.length >= 2) {
                    String ownerName = values[0];
                    // Ищем мага, который уже есть в миссии
                    mission.getSorcerers().stream()
                            .filter(s -> s.getName().equalsIgnoreCase(ownerName))
                            .findFirst()
                            .ifPresent(owner -> {
                                Technique tech = new Technique();
                                tech.setName(values[1]);
                                tech.setType(values.length > 2 ? values[2] : "Innate");
                                tech.setDamage(values.length > 3 ? Long.parseLong(values[3]) : 0L);

                                // Привязываем технику к магу (двусторонняя связь)
                                owner.addTechnique(tech);
                            });
                }
                break;

            case "MISSION_RESULT":
                try {
                    mission.setOutcome(Outcome.valueOf(values[0].toUpperCase()));
                } catch (Exception e) {
                    mission.setOutcome(Outcome.UNKNOWN);
                }
                break;

            // --- ОБРАБОТКА ДОП. ПОЛЕЙ (чтобы не падало) ---

            case "TAGS":
                for (String v : values) mission.getOperationTags().add(v.trim());
                break;

            case "SUPPORT":
                for (String v : values) mission.getSupportUnits().add(v.trim());
                break;

            case "RECOMMENDATION":
                for (String v : values) mission.getRecommendations().add(v.trim());
                break;

            case "STATUS_EFFECT":
                // Формат: STATUS_EFFECT | Название | Описание
                if (values.length >= 2) {
                    mission.getStatusEffects().put(values[0].trim(), values[1].trim());
                }
                break;

            default:
                // Если поле совсем неизвестно (например, "WEATHER" или "UNKNOWN_DATA")
                // Мы просто записываем это в текстовое поле notes, чтобы данные не пропали
                String unknownInfo = key + ": " + String.join(", ", values);
                if (mission.getNotes() == null) {
                    mission.setNotes(unknownInfo);
                } else {
                    mission.setNotes(mission.getNotes() + " | " + unknownInfo);
                }
                break;
        }
    }
}
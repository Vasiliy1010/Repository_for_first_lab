package ru.labs.hm1.output;

import ru.labs.hm1.mission.Mission;

import java.util.Map;

public class SummaryMap implements Summary {
    Summary sum;
    private Mission mission;

    public SummaryMap(Summary sum, Mission mission){
        this.sum = sum;
        this.mission = mission;
    }

    @Override
    public void getSummary() {
        sum.getSummary();
        try {
            System.out.println("Дополнительная информация");
            Map<String, Object> data = mission.getExtraFields();
            for (String key : data.keySet()) {
                Object value = data.get(key);
                if (data.get(key) instanceof Map) {
                    Map<String, Object> valueMap = (Map<String, Object>) value;
                    for (String subKey : valueMap.keySet()) {
                        System.out.println(subKey + ": " + valueMap.get(subKey));
                    }
                } else {
                    System.out.println(key + ": " + value);
                }
            }
        } catch (Exception e) {
            System.out.println("Дополнительная информация отсутствует");
        }
    }
}

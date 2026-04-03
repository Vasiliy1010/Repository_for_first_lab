package ru.labs.hm1.output;

import ru.labs.hm1.mission.Mission;

import java.util.List;
import java.util.Map;

public class SummaryList implements Summary {
    Summary sum;
    private Mission mission;

    public SummaryList(Summary sum, Mission mission){
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
                System.out.print("\n" + key);
                if (value instanceof Map) {
                    Map<String, Object> valueMap = (Map<String, Object>) value;
                    System.out.println();
                    for (String subKey : valueMap.keySet()) {
                        System.out.println(subKey + ": " + valueMap.get(subKey));
                    }
                } else if (value instanceof List) {
                    List<Object> list = (List<Object>) value;
                    for (Object item : list) {
                        if (item instanceof Map) {
                            Map<String, Object> itemMap = (Map<String, Object>) item;
                            for (Object listKey : itemMap.keySet()) {
                                System.out.println();
                                System.out.print(listKey + ": " + itemMap.get(listKey) + " ");
                            }
                        } else {
                            System.out.println(item);
                        }
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

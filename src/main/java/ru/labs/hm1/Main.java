package ru.labs.hm1;

import ru.labs.hm1.mission.Mission;
import ru.labs.hm1.parsing.MissionParser;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите путь к файлу");
        String inputPath = scanner.nextLine();
        String outputPath = "D:/Labs/LabsJava/FirstLab/result.json";
        try {
            MissionParser parser = ParserFactory.getParser(inputPath);
            System.out.println("Начинаем парсинг файла: " + inputPath);
            Mission mission = parser.loadMission(inputPath);
            mission.displaySummary();
            MissionSaver.saveToJson(mission, outputPath);
        } catch (Exception e) {
            System.err.println("Произошла ошибка");
        } finally {
            scanner.close();
        }
    }
}
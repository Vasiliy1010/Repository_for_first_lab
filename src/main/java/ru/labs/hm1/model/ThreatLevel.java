package ru.labs.hm1.model;

public enum ThreatLevel {
    LOW,
    MEDIUM,
    HIGH,
    SPECIAL_GRADE,
    UNKNOWN;

    public static ThreatLevel fromString(String str) {
        if (str == null){
            return UNKNOWN;
        }
        try {
            return ThreatLevel.valueOf(str.toUpperCase().trim().replace(' ', '_'));
        } catch (Exception e) {
            return UNKNOWN;
        }
    }
}

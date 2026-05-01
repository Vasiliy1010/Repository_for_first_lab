package ru.labs.hm1.model;

public enum TechniqueType {
    INNATE,
    CURSED_TOOL,
    SHIKIGAMI,
    BARRIER,
    BODY,
    WEAPON,
    UNKNOWN;

    public static TechniqueType fromString(String str) {
        if (str == null) return UNKNOWN;
        try {
            return TechniqueType.valueOf(str.toUpperCase().trim());
        } catch (Exception e) {
            return UNKNOWN;
        }
    }

}

package ru.labs.hm1.model;

public enum Outcome {
    SUCCESS,
    FAILURE,
    PARTIAL_SUCCESS,
    UNKNOWN;

    public static Outcome fromString(String str) {
        if (str == null) return UNKNOWN;
        try {
            return Outcome.valueOf(str.toUpperCase().trim());
        } catch (Exception e) {
            return UNKNOWN;
        }
    }
}

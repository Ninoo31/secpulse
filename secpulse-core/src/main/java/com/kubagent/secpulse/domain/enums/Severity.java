package com.kubagent.secpulse.domain.enums;

public enum Severity {
    CRITICAL(25), HIGH(10), MEDIUM(3), LOW(1), UNKNOWN(-1);

    private final int weight;

    Severity(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public static Severity from(String value) {
        if (value == null) return UNKNOWN;

        try {
            return Severity.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}

package com.kubagent.secpulse.domain.enums;

public enum Severity {
    CRITICAL(25), HIGH(10), MEDIUM(3), LOW(1);

    Severity(int weight) {
    }
}

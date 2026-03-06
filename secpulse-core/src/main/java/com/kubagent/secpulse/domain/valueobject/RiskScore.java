package com.kubagent.secpulse.domain.valueobject;

public record RiskScore(int value) {
    public RiskScore{
        if(value <0 || value > 100) {
            throw new IllegalArgumentException("Score must be 0-100");
        }
    }
}

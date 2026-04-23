package com.kubagent.secpulse.domain.valueobject;

public record RiskScore(int value) {
    public RiskScore{
        if(value < 0 || value > 100) {
            throw new IllegalArgumentException("Score must be 0-100");
        }
    }

    public String getLevel() {
        if (this.value <= 30) return "LOW";
        if (this.value <= 70) return "MEDIUM";
        return "HIGH";
    }

    public RiskScore add(RiskScore other) {
        int value = this.value + other.value;
        if (value > 100) {
            value = 100;
        }
        return new RiskScore(value);
    }
    // ou return new RiskScore(Math.min(this.value + other.value, 100));
}

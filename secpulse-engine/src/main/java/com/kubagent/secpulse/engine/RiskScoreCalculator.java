package com.kubagent.secpulse.engine;

import com.kubagent.secpulse.domain.valueobject.RiskScore;
import com.kubagent.secpulse.dto.response.ScanResult;

import java.util.List;

public class RiskScoreCalculator {

    public RiskScore calculate(List<ScanResult.Finding> findings) {
        int total = findings.stream().mapToInt(x -> x.severity().getWeight()).sum();
        return  new RiskScore(Math.min(total, 100));
    }
}

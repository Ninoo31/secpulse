package com.kubagent.secpulse.dto.response;

import com.kubagent.secpulse.domain.valueobject.RiskScore;
import com.kubagent.secpulse.dto.request.ScanTarget;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public record ScanReport(ScanTarget target, List<ScanResult> results, RiskScore globalRiskScore, LocalDateTime completedAt) {
}

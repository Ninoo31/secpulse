package com.kubagent.secpulse.dto.response;

import com.kubagent.secpulse.domain.enums.Severity;
import com.kubagent.secpulse.dto.request.ScanTarget;

import java.time.LocalDateTime;
import java.util.List;

public record ScanResult(String scannerName, ScanTarget target, List<Finding> findings, LocalDateTime scannedAt, boolean success) {
    public record Finding(String title, Severity severity, String description, String remediation) {}
}

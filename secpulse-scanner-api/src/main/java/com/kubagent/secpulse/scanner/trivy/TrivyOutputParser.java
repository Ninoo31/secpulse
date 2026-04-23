package com.kubagent.secpulse.scanner.trivy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kubagent.secpulse.domain.enums.Severity;
import com.kubagent.secpulse.dto.response.ScanResult;
import com.kubagent.secpulse.scanner.trivy.dto.TrivyReport;

import java.util.List;
import java.util.Optional;

public class TrivyOutputParser {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<ScanResult.Finding> parse(String json) throws JsonProcessingException {
        TrivyReport report = objectMapper.readValue(json, TrivyReport.class);

        return Optional.ofNullable(report.results())
                .orElse(List.of())
                .stream()
                .filter(result -> result.vulnerabilities() != null)
                .flatMap(result -> result.vulnerabilities().stream())
                .map(vuln -> new ScanResult.Finding(
                        vuln.vulnerabilityId(),
                        Severity.from(vuln.severity()),
                        vuln.description(),
                        "Fix version: " + vuln.fixedVersion()
                ))
                .toList();
    }
}
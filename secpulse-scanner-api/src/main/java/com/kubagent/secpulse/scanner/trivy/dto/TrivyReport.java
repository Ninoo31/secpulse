package com.kubagent.secpulse.scanner.trivy.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TrivyReport(
        @JsonProperty("Results")
        List<TrivyResult> results
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record TrivyResult(
            @JsonProperty("Vulnerabilities")
            List<TrivyVulnerability> vulnerabilities
    ) {
        public record TrivyVulnerability(
                @JsonProperty("VulnerabilityId") String vulnerabilityId,
                @JsonProperty("Severity") String severity,
                @JsonProperty("Title") String title,
                @JsonProperty("Description") String description,
                @JsonProperty("FixedVersion") String fixedVersion) {}
    }
}

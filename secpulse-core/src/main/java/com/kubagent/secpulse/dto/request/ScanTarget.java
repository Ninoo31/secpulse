package com.kubagent.secpulse.dto.request;

import com.kubagent.secpulse.domain.enums.TargetType;

import java.util.UUID;

public record ScanTarget(TargetType type, String location, UUID projectId) {}

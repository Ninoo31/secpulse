package com.kubagent.secpulse.port;

import com.kubagent.secpulse.dto.response.ScanResult;
import com.kubagent.secpulse.dto.request.ScanTarget;

public interface ScannerPlugin {
    String getName();
    ScanResult scan(ScanTarget target);
    boolean supports(ScanTarget target);
}

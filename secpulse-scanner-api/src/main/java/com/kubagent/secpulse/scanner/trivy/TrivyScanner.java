package com.kubagent.secpulse.scanner.trivy;

import com.kubagent.secpulse.dto.request.ScanTarget;
import com.kubagent.secpulse.dto.response.ScanResult;
import com.kubagent.secpulse.port.ScannerPlugin;

import java.time.LocalDateTime;
import java.util.List;

public class TrivyScanner implements ScannerPlugin {
    private final String NAME= "trivy";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public ScanResult scan(ScanTarget target) {
        return new ScanResult(getName(), target, List.of(), LocalDateTime.now(), true);
    }

    @Override
    public boolean supports(ScanTarget target) {
        return switch (target.type()){
            case DOCKER_IMAGE, FILESYSTEM -> true;
            default -> false;
        };
    }
}

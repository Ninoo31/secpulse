package com.kubagent.secpulse.engine;

import com.kubagent.secpulse.domain.valueobject.RiskScore;
import com.kubagent.secpulse.dto.request.ScanTarget;
import com.kubagent.secpulse.dto.response.ScanReport;
import com.kubagent.secpulse.dto.response.ScanResult;
import com.kubagent.secpulse.port.ScannerPlugin;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ScanOrchestrator {

    private final List<ScannerPlugin> scanners;
    private final RiskScoreCalculator calculator;

    // Constructeur par injection
    public ScanOrchestrator(List<ScannerPlugin> scanners, RiskScoreCalculator calculator) {
        this.scanners = scanners;
        this.calculator = calculator;
    }

    public ScanReport orchestrate(ScanTarget target) {

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Future<ScanResult>> futures = scanners.stream()
                    .filter(sp -> sp.supports(target))
                    .map(sp -> executor.submit(() -> sp.scan(target)))
                    .toList();
            List<ScanResult> results = futures.stream()
                    .map(f -> {
                        try {
                            return f.get();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }).toList();

            RiskScore globalRiskScore = calculator.calculate(results.stream()
                    .flatMap(f -> f.findings().stream()).toList());

            return new ScanReport(target, results, globalRiskScore, LocalDateTime.now());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}

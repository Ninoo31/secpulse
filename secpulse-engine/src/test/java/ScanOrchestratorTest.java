import com.kubagent.secpulse.domain.enums.Severity;
import com.kubagent.secpulse.domain.enums.TargetType;
import com.kubagent.secpulse.dto.request.ScanTarget;
import com.kubagent.secpulse.dto.response.ScanReport;
import com.kubagent.secpulse.dto.response.ScanResult;
import com.kubagent.secpulse.engine.RiskScoreCalculator;
import com.kubagent.secpulse.engine.ScanOrchestrator;
import com.kubagent.secpulse.port.ScannerPlugin;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class ScanOrchestratorTest {

    ScanTarget target = new ScanTarget(TargetType.DOCKER_IMAGE, "nginx:latest", UUID.randomUUID());
    RiskScoreCalculator calculator = new RiskScoreCalculator();

    @Test
    void shouldOnlyCallCompatibleScanners() {
        // GIVEN
        ScannerPlugin scanner1 = mock(ScannerPlugin.class);
        ScannerPlugin scanner2 = mock(ScannerPlugin.class);

        when(scanner1.supports(target)).thenReturn(true);
        when(scanner1.scan(target)).thenReturn(
                new ScanResult("scanner1", target, List.of(), LocalDateTime.now(), true)
        );
        when(scanner2.supports(target)).thenReturn(false);

        ScanOrchestrator orchestrator = new ScanOrchestrator(
                List.of(scanner1, scanner2), calculator
        );

        // WHEN
        orchestrator.orchestrate(target);

        // THEN
        verify(scanner1, times(1)).scan(target);   // scanner1 appelé 1 fois
        verify(scanner2, never()).scan(target);     // scanner2 jamais appelé
    }

    @Test
    void shouldReturnPositiveRiskScoreForCriticalFindings() {
        // GIVEN
        ScannerPlugin scanner = mock(ScannerPlugin.class);

        List<ScanResult.Finding> findings = List.of(
                new ScanResult.Finding("CVE-001", Severity.CRITICAL, "desc", "fix"),
                new ScanResult.Finding("CVE-002", Severity.CRITICAL, "desc", "fix")
        );

        when(scanner.supports(target)).thenReturn(true);
        when(scanner.scan(target)).thenReturn(
                new ScanResult("trivy", target, findings, LocalDateTime.now(), true)
        );

        ScanOrchestrator orchestrator = new ScanOrchestrator(
                List.of(scanner), calculator
        );

        // WHEN
        ScanReport report = orchestrator.orchestrate(target);

        // THEN
        Assertions.assertEquals(50, report.globalRiskScore().value());
        Assertions.assertEquals(1, report.results().size());
    }
}
import com.kubagent.secpulse.dto.response.ScanResult;
import com.kubagent.secpulse.scanner.trivy.TrivyOutputParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class TrivyOutputParserTest {

    private TrivyOutputParser parser;

    @BeforeEach
    void init() {
        parser = new TrivyOutputParser();
    }

    // Test parsing d'un json correct
    @Test
    void shouldParseFindingsFromValidJson() throws Exception {
        String json = """
                {
                  "Results": [
                    {
                      "Vulnerabilities": [
                        {
                          "VulnerabilityID": "CVE-2021-44228",
                          "Severity": "CRITICAL",
                          "Title": "Log4Shell",
                          "Description": "RCE via Log4j",
                          "FixedVersion": "2.15.0"
                        }
                      ]
                    }
                  ]
                }
                """;

        List<ScanResult.Finding> findings = parser.parse(json);
        assertThat(findings).hasSize(1);

        ScanResult.Finding finding = findings.getFirst();
    }

    // Test parsing d'un json avec des valeurs null
    @Test
    void shouldReturnEmptyListWhenNoVulnerabilities() throws Exception {
        // GIVEN — un JSON Trivy sans vulnérabilités
        String json = """
                {
                  "Results": [
                    {
                      "Vulnerabilities": null
                    }
                  ]
                }
                """;

        // WHEN
        List<ScanResult.Finding> findings = parser.parse(json);

        // THEN
        assertThat(findings).isEmpty();
    }

    @Test
    void shouldReturnEmptyListWhenResultsIsNull() throws Exception {
        String json = "{}";
        List<ScanResult.Finding> findings = parser.parse(json);
        assertThat(findings).isEmpty();
    }

    @Test
    void shouldHandleMultipleVulnerabilities() throws Exception {
        String json = """
                {
                  "Results": [
                    {
                      "Vulnerabilities": [
                        {
                          "VulnerabilityID": "CVE-2021-44228",
                          "Severity": "CRITICAL",
                          "Title": "Log4Shell",
                          "Description": "RCE via Log4j",
                          "FixedVersion": "2.15.0"
                        },
                       {
                          "VulnerabilityID": "CVE-2024-44228",
                          "Severity": "MEDIUM",
                          "Title": "Log4Shell",
                          "Description": "RCE via Log4j",
                          "FixedVersion": "2.15.0"
                        } 
                      ]
                    }
                  ]
                }
                """;

        List<ScanResult.Finding> findings = parser.parse(json);
        assertThat(findings).hasSize(2);
    }
}
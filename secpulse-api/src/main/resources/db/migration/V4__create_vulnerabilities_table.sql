CREATE TABLE vulnerabilities (
    id                      UUID            PRIMARY KEY DEFAULT gen_random_uuid(),
    scan_result_id          UUID            NOT NULL REFERENCES scan_results(id),
    vulnerability_id        VARCHAR(50),
    severity                VARCHAR(50),
    title                   VARCHAR(500),
    description             TEXT,
    remediation             TEXT,
    risk_score              INTEGER         CHECK(vulnerabilities.risk_score BETWEEN 0 AND 100),
    created_at              TIMESTAMP       NOT NULL DEFAULT now()
)
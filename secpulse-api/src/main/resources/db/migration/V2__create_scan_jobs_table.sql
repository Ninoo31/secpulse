CREATE TABLE scan_jobs(
    id              UUID    PRIMARY KEY DEFAULT gen_random_uuid(),
    project_id      UUID    NOT NULL REFERENCES projects(id),
    scanner_type    VARCHAR(50),
    status          VARCHAR(50),
    started_at      TIMESTAMP,
    completed_at    TIMESTAMP,
    created_at      TIMESTAMP DEFAULT now()
)
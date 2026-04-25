CREATE TABLE scan_results(
      id                UUID    PRIMARY KEY DEFAULT gen_random_uuid(),
      scan_job_id       UUID    NOT NULL REFERENCES scan_jobs(id),
      severity          VARCHAR(50),
      category          VARCHAR(100),
      resource_name     VARCHAR(255),
      raw_results       JSONB,
      created_at      TIMESTAMP NOT NULL DEFAULT now()
)
CREATE TABLE projects(
    id              UUID            PRIMARY KEY DEFAULT gen_random_uuid(),
    name            VARCHAR(255)    not null,
    repository_url  VARCHAR(500),
    description     TEXT,
    created_at      TIMESTAMP       not null DEFAULT now(),
    updated_at      TIMESTAMP       DEFAULT now()
)
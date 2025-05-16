CREATE TABLE IF NOT EXISTS site_details (
    site_id TEXT NOT NULL PRIMARY KEY,
    service_name VARCHAR(25) NOT NULL,
    service_health_check_endpoint VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS ping_log (
    ping_id TEXT PRIMARY KEY,
    http_response_status INT NOT NULL,
    response_time_ms INT NOT NULL,
    is_success BOOLEAN NOT NULL,
    site_id TEXT NOT NULL,
    created_at TEXT NOT NULL,
    FOREIGN KEY (site_id) REFERENCES site_details(site_id)
);
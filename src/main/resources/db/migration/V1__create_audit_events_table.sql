CREATE TABLE audit_events (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    booking_id VARCHAR(50) NOT NULL,
    action VARCHAR(50) NOT NULL,
    actor_id VARCHAR(50) NOT NULL,
    actor_role VARCHAR(30) NOT NULL,
    event_timestamp DATETIME NOT NULL,
    detail VARCHAR(1000),
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_audit_booking ON audit_events(booking_id);
CREATE INDEX idx_audit_actor ON audit_events(actor_id);
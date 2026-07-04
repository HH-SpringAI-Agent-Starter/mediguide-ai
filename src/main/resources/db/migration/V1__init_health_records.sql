CREATE EXTENSION IF NOT EXISTS vector;

CREATE TABLE IF NOT EXISTS health_records (
    id BIGSERIAL PRIMARY KEY,
    patient_id VARCHAR(64) NOT NULL,
    record_type VARCHAR(32) NOT NULL,
    recorded_at TIMESTAMP NOT NULL DEFAULT NOW(),
    systolic INT,
    diastolic INT,
    heart_rate INT,
    blood_sugar DECIMAL(4,1),
    weight DECIMAL(5,1),
    symptoms TEXT,
    notes TEXT
);

CREATE INDEX idx_health_records_patient ON health_records(patient_id, recorded_at DESC);

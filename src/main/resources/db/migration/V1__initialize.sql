CREATE TABLE raw (
    id              BIGSERIAL PRIMARY KEY,
    data            TEXT NOT NULL
);

CREATE TABLE content (
    id              BIGSERIAL PRIMARY KEY,
    tag             VARCHAR(255) NOT NULL,
    content         VARCHAR(255),
    raw_id          BIGINT REFERENCES raw (id)
);

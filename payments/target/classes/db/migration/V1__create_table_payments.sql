CREATE TABLE payments (
    id BIGSERIAL PRIMARY KEY,
    value NUMERIC(19,2) NOT NULL,
    name VARCHAR(100),
    number VARCHAR(19),
    expiration VARCHAR(7),
    code VARCHAR(3),
    status VARCHAR(255) NOT NULL,
    payment_method_id BIGINT NOT NULL,
    request_id BIGINT NOT NULL
);
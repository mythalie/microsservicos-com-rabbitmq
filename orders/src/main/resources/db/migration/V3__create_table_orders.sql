CREATE TABLE orders (
  id BIGSERIAL PRIMARY KEY,
  datetime timestamp NOT NULL,
  status varchar(255) NOT NULL
);
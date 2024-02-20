CREATE TABLE order_item (
  id BIGSERIAL PRIMARY KEY,
  description varchar(255) DEFAULT NULL,
  amount int NOT NULL,
  order_id bigint NOT NULL,
  FOREIGN KEY (order_id) REFERENCES orders(id)
);
-- 用户表
CREATE TABLE users
(
  user_id           BIGINT PRIMARY KEY,
  username          VARCHAR(100),
  email             VARCHAR(200),
  registration_date DATE,
  city              VARCHAR(100),
  country           VARCHAR(100)
);

-- 商品表
CREATE TABLE products
(
  product_id     BIGINT PRIMARY KEY,
  product_name   VARCHAR(200),
  category       VARCHAR(100),
  subcategory    VARCHAR(100),
  price          DECIMAL(10, 2),
  stock_quantity INT,
  created_date   DATE
);

-- 订单表
CREATE TABLE orders
(
  order_id     BIGINT PRIMARY KEY,
  user_id      BIGINT,
  order_date   TIMESTAMP,
  total_amount DECIMAL(10, 2),
  status       VARCHAR(50),
  FOREIGN KEY (user_id) REFERENCES users (user_id)
);

-- 订单明细表
CREATE TABLE order_items
(
  order_item_id BIGINT PRIMARY KEY,
  order_id      BIGINT,
  product_id    BIGINT,
  quantity      INT,
  unit_price    DECIMAL(10, 2),
  subtotal      DECIMAL(10, 2),
  FOREIGN KEY (order_id) REFERENCES orders (order_id),
  FOREIGN KEY (product_id) REFERENCES products (product_id)
);

-- 用户行为表
CREATE TABLE user_behavior
(
  behavior_id   BIGINT PRIMARY KEY,
  user_id       BIGINT,
  product_id    BIGINT,
  behavior_type VARCHAR(50), -- 'view', 'cart', 'purchase'
  behavior_time TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users (user_id),
  FOREIGN KEY (product_id) REFERENCES products (product_id)
);

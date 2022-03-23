CREATE TABLE db_view;
CREATE VIEW db_view.${tableName} AS SELECT * FROM ${dbName}.${tableName};

-- 数据库1：订单中心
CREATE DATABASE ordercenter;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
-- ----------------------------
-- Table structure for orderitems
-- ----------------------------
DROP TABLE IF EXISTS `orderitems`;
CREATE TABLE `orderitems`  (
  `order_num` int(11) NOT NULL COMMENT '订单号',
  `order_item` int(11) NOT NULL COMMENT '订单物品号，在订单中的顺序',
  `prod_id` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '产品ID，外键关联产品表主键',
  `quantity` int(11) NOT NULL COMMENT '物品数量',
  `item_price` decimal(8, 2) NOT NULL COMMENT '物品价格',
  PRIMARY KEY (`order_num`, `order_item`) USING BTREE,
  INDEX `idx_prod_quan_price`(`prod_id`, `quantity`, `item_price`) USING BTREE,
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单详情表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

-- 产品中心
CREATE DATABASE productcenter;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for products
-- ----------------------------
DROP TABLE IF EXISTS `products`;
CREATE TABLE `products`  (
  `prod_id` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `vend_id` int(11) NOT NULL,
  `prod_name` char(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `prod_price` decimal(8, 2) NOT NULL,
  `prod_desc` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  PRIMARY KEY (`prod_id`) USING BTREE,
  INDEX `fk_products_vendors`(`vend_id`) USING BTREE,
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '产品表' ROW_FORMAT = Dynamic;
SET FOREIGN_KEY_CHECKS = 1;
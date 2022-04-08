-- 视图库 bussiness
DROP DATABASE IF EXISTS `bussiness`;
CREATE DATABASE IF NOT EXISTS `bussiness` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

-- 产品中心
DROP DATABASE IF EXISTS `productcenter`;
CREATE DATABASE IF NOT EXISTS `productcenter` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE productcenter;
CREATE TABLE `t_product` (
  `prod_id` char(10) NOT NULL COMMENT '产品唯一主键ID',
  `vend_id` int(11) NOT NULL COMMENT '外键引用供应商唯一主键ID',
  `prod_name` char(255) NOT NULL COMMENT '产品名',
  `prod_price` decimal(8,2) NOT NULL COMMENT '产品价格',
  `prod_desc` text COMMENT '产品描述信息',
  PRIMARY KEY (`prod_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='产品表';

INSERT INTO `t_product` VALUES ('ANV01', 1001, '.5 ton anvil', 5.99, '.5 ton anvil, black, complete with handy hook');
INSERT INTO `t_product` VALUES ('ANV02', 1001, '1 ton anvil', 9.99, '1 ton anvil, black, complete with handy hook and carrying case');
INSERT INTO `t_product` VALUES ('ANV03', 1001, '2 ton anvil', 14.99, '2 ton anvil, black, complete with handy hook and carrying case');
INSERT INTO `t_product` VALUES ('DTNTR', 1003, 'Detonator', 13.00, 'Detonator (plunger powered), fuses not included');
INSERT INTO `t_product` VALUES ('FB', 1003, 'Bird seed', 10.00, 'Large bag (suitable for road runners)');
INSERT INTO `t_product` VALUES ('FC', 1003, 'Carrots', 2.50, 'Carrots (rabbit hunting season only)');
INSERT INTO `t_product` VALUES ('FU1', 1002, 'Fuses', 3.42, '1 dozen, extra long');
INSERT INTO `t_product` VALUES ('JP1000', 1005, 'JetPack 1000', 35.00, 'JetPack 1000, intended for single use');
INSERT INTO `t_product` VALUES ('JP2000', 1005, 'JetPack 2000', 55.00, 'JetPack 2000, multi-use');
INSERT INTO `t_product` VALUES ('OL1', 1002, 'Oil can', 8.99, 'Oil can, red');
INSERT INTO `t_product` VALUES ('SAFE', 1003, 'Safe', 50.00, 'Safe with combination lock');
INSERT INTO `t_product` VALUES ('SLING', 1003, 'Sling', 4.49, 'Sling, one size fits all');
INSERT INTO `t_product` VALUES ('TNT1', 1003, 'TNT (1 stick)', 2.50, 'TNT, red, single stick');
INSERT INTO `t_product` VALUES ('TNT2', 1003, 'TNT (5 sticks)', 10.00, 'TNT, red, pack of 10 sticks');

-- 订单中心
DROP DATABASE IF EXISTS `ordercenter`;
CREATE DATABASE IF NOT EXISTS `ordercenter` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE ordercenter;
-- 订单表
CREATE TABLE `t_order` (
  `order_num` int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一订单号',
  `order_date` datetime NOT NULL COMMENT '订单日期',
  `cust_id` int(11) NOT NULL COMMENT '订单顾客ID',
  PRIMARY KEY (`order_num`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=20010 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `t_order` VALUES (20005, '2005-09-01 00:00:00', 10001);
INSERT INTO `t_order` VALUES (20006, '2005-09-12 00:00:00', 10003);
INSERT INTO `t_order` VALUES (20007, '2005-09-30 00:00:00', 10004);
INSERT INTO `t_order` VALUES (20008, '2005-10-03 00:00:00', 10005);
INSERT INTO `t_order` VALUES (20009, '2005-10-08 00:00:00', 10001);

-- 订单详情表
CREATE TABLE `t_order_item` (
  `order_num` int(11) NOT NULL COMMENT '订单号，关联订单表的order_num',
  `order_item` int(11) NOT NULL COMMENT '订单物品号，在某个订单中的顺序',
  `prod_id` char(10) NOT NULL COMMENT '产品ID(关联产品表的prod_id)',
  `quantity` int(11) NOT NULL COMMENT '物品数量',
  `item_price` decimal(8,2) NOT NULL COMMENT '物品价格',
  PRIMARY KEY (`order_num`,`order_item`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='订单详情表，订单表中1调数据对应订单详情表中多条数据';

-- ----------------------------
-- Records of orderitems
-- ----------------------------
INSERT INTO `t_order_item` VALUES (20005, 1, 'ANV01', 10, 5.99);
INSERT INTO `t_order_item` VALUES (20005, 2, 'ANV02', 3, 9.99);
INSERT INTO `t_order_item` VALUES (20005, 3, 'TNT2', 5, 10.00);
INSERT INTO `t_order_item` VALUES (20005, 4, 'FB', 1, 10.00);
INSERT INTO `t_order_item` VALUES (20006, 1, 'JP2000', 1, 55.00);
INSERT INTO `t_order_item` VALUES (20007, 1, 'TNT2', 100, 10.00);
INSERT INTO `t_order_item` VALUES (20008, 1, 'FC', 50, 2.50);
INSERT INTO `t_order_item` VALUES (20009, 1, 'FB', 1, 10.00);
INSERT INTO `t_order_item` VALUES (20009, 2, 'OL1', 1, 8.99);
INSERT INTO `t_order_item` VALUES (20009, 3, 'SLING', 1, 4.49);
INSERT INTO `t_order_item` VALUES (20009, 4, 'ANV03', 1, 14.99);

-- 创建视图
CREATE VIEW bussiness.t_product AS SELECT * FROM productcenter.t_product;
CREATE VIEW bussiness.t_order AS SELECT * FROM ordercenter.t_order;
CREATE VIEW bussiness.t_order_item AS SELECT * FROM ordercenter.t_order_item;
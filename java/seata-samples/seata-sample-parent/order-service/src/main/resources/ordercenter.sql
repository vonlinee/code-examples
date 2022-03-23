CREATE
DATABASE `ordercenter` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

-- 订单服务DB执行
CREATE TABLE `tab_order`
(
    `id`         bigint(11) NOT NULL AUTO_INCREMENT,
    `user_id`    bigint(11) DEFAULT NULL COMMENT '用户id',
    `product_id` bigint(11) DEFAULT NULL COMMENT '产品id',
    `count`      int(11) DEFAULT NULL COMMENT '数量',
    `money`      decimal(11, 0) DEFAULT NULL COMMENT '金额',
    `status`     int(1) DEFAULT NULL COMMENT '订单状态：0：创建中；1：已完成',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

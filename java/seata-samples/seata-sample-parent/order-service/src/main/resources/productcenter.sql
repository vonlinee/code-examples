CREATE
DATABASE IF NOT EXISTS `productcenter` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

-- 库存服务DB执行
CREATE TABLE `tab_storage`
(
    `id`         bigint(11) NOT NULL AUTO_INCREMENT,
    `product_id` bigint(11) DEFAULT NULL COMMENT '产品id',
    `total`      int(11) DEFAULT NULL COMMENT '总库存',
    `used`       int(11) DEFAULT NULL COMMENT '已用库存',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
INSERT INTO `tab_storage` (`product_id`, `total`, `used`)
VALUES ('1', '96', '4');
INSERT INTO `tab_storage` (`product_id`, `total`, `used`)
VALUES ('2', '100', '0');

DROP TABLE IF EXISTS `connection_config`;
CREATE TABLE `connection_config`
(
    `id`       varchar(36) NOT NULL COMMENT '主键ID',
    `name`     varchar(255) DEFAULT NULL COMMENT '连接名称',
    `host`     varchar(255) DEFAULT NULL COMMENT '主机地址，IP地址',
    `port`     varchar(255) DEFAULT NULL COMMENT '端口号',
    `db_type`  varchar(255) DEFAULT NULL COMMENT '数据库类型',
    `db_name`  varchar(255) DEFAULT NULL COMMENT '数据库名称',
    `username` varchar(255) DEFAULT NULL COMMENT '用户名',
    `password` varchar(255) DEFAULT NULL COMMENT '密码',
    `encoding` varchar(255) DEFAULT NULL COMMENT '连接编码',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `t_account` (
  `id` int(3) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `money` float DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
INSERT INTO db_mysql.t_account (id, name, money) VALUES(1, 'zs', 10000.0);
INSERT INTO db_mysql.t_account (id, name, money) VALUES(2, 'ls', 2000.0);


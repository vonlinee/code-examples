CREATE TABLE `t_account` (
  `id` int(3) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `money` float DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT


INSERT INTO db_mysql.t_account
(name, money)
VALUES('zs', 5000);
INSERT INTO db_mysql.t_account
(name, money)
VALUES('ls', 2000);
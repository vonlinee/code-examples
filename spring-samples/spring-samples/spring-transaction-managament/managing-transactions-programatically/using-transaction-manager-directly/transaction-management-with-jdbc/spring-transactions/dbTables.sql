-- 数据库：db_mysql
DROP TABLE IF EXISTS t_account;
CREATE TABLE `t_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(10) NOT NULL,
  `money` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

INSERT INTO `db_mysql`.`t_account`(`id`, `name`, `money`) VALUES (1, 'zs', 5000.00);
INSERT INTO `db_mysql`.`t_account`(`id`, `name`, `money`) VALUES (2, 'ls', 2000.00);
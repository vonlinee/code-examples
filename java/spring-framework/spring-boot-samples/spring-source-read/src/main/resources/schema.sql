CREATE TABLE `user_account`
(
    `id`    int(11) NOT NULL AUTO_INCREMENT,
    `name`  varchar(100) DEFAULT NULL,
    `money` int(11) DEFAULT '0',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
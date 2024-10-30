CREATE TABLE `account`
(
    `id`   varchar(10) DEFAULT NULL,
    `name` varchar(10) DEFAULT NULL,
    `money` double (14,4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
INSERT INTO account
VALUES (1, 'zs', 5000.00);
INSERT INTO account
VALUES (2, 'ls', 3000.00);
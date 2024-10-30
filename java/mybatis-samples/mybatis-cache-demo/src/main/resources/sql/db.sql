CREATE DATABASE IF NOT EXISTS test_db DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_general_ci;

USE test_db

DROP TABLE IF EXISTS `class`;
CREATE TABLE `class`
(
    `id`   int(11) unsigned NOT NULL AUTO_INCREMENT,
    `name` varchar(20) COLLATE utf8_bin DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO `class` (`id`, `name`)
VALUES (1, '一班'),
       (2, '二班');

DROP TABLE IF EXISTS `classroom`;
CREATE TABLE `classroom`
(
    `id`         int(11) unsigned NOT NULL AUTO_INCREMENT,
    `class_id`   int(11) DEFAULT NULL,
    `student_id` int(11) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO `classroom` (`id`, `class_id`, `student_id`)
VALUES (1, 1, 1),
       (2, 1, 2),
       (3, 2, 3),
       (4, 2, 4);

DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`
(
    `id`   int(11) unsigned NOT NULL AUTO_INCREMENT,
    `name` varchar(200) COLLATE utf8_bin DEFAULT NULL,
    `age`  tinyint(3) unsigned DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO `student` (`id`, `name`, `age`)
VALUES (1, '点点', 16),
       (2, '平平', 16),
       (3, '美美', 16),
       (4, '团团', 16);

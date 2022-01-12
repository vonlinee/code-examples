-- 创建数据库
CREATE DATABASE IF NOT EXISTS `mybatis_learn` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
-- 建表及填充数据
-- 课程表
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `cid` int(11) NOT NULL,
  `cname` varchar(30) DEFAULT NULL,
  `tid` int(11) DEFAULT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- 分数表
DROP TABLE IF EXISTS `score`;
CREATE TABLE `score` (
  `sid` int(11) NOT NULL,
  `cid` int(11) NOT NULL,
  `score` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` int(11) NOT NULL,
  `name` varchar(30) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `sex` varchar(8) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SELECT * FROM student;
INSERT INTO student VALUES (1, "韩信", 29, "1");
INSERT INTO student VALUES (2, "小乔", 28, "-1");
INSERT INTO student VALUES (3, "花木兰", 24, "-1");


DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher` (
  `id` int(11) NOT NULL,
  `name` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `class`;
CREATE TABLE `class` (
  `ID` int(11) NOT NULL,
  `CLASS_NAME` varchar(30) DEFAULT NULL
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;






-- 其他可能用的表
-- 英雄表
DROP TABLE IF EXISTS `t_hero`;
CREATE TABLE `t_hero`  (
  `ID` int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `NAME` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `HP` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0',
  `DAMAGE` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;


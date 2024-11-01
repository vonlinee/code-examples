-- New script in localhost.
-- Date: 2022年1月12日
-- Time: 下午7:27:58
-- 数据库需要有以下信息：
-- 学生：学号、单位名称、姓名、性别、年龄、选修课程名
-- 课程：编号、课程名、开课学院、任课教师号
-- 教师：教师号、姓名、性别、职称、讲授课程编号
-- 单位：单位名称、电话、教师号、教师姓名
-- 公寓：
-- 上述实体中存在如下联系：
-- （1）一个学生可选修多门课程，一门课程可被多个学生选修。
-- （2）一个教师可讲授多门课程，一门课程可由多个教师讲授。
-- （3）一个单位可有多个教师，一个教师只能属于一个单位。

-- ID：32位UUID，16位编号

-- 班级表
DROP TABLE IF EXISTS `t_class`;
CREATE TABLE `t_class` (
  `CLASS_ID` int(11) NOT NULL AUTO_INCREMENT,
  `CLASS_NO` varchar(10) NOT NULL AUTO_INCREMENT,
  `CLASS_NAME` varchar(50) NOT NULL,
  `DEPART_NO` varchar(100) DEFAULT NULL,
  `DEPART_NAME` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`CLASS_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
-- 课程表
DROP TABLE IF EXISTS `t_cource`;
CREATE TABLE `t_cource` (
  `COURSE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `COURSE_NO` varchar(36) NOT NULL DEFAULT "",
  `COURSE_NAME` varchar(100) NOT NULL,
  `COURSE_TIME_LONG` float DEFAULT NULL COMMENT "课程时长",
  `TEACHER_NO` varchar(100) DEFAULT NULL,
  `DEPART_NO` varchar(100) DEFAULT NULL COMMENT "课程所开设院系ID",
  PRIMARY KEY (`courceId`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
-- 院系表
DROP TABLE IF EXISTS `t_depart`;
CREATE TABLE `t_depart` (
  `DEPARTMENT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `DEPARTMENT_NO` varchar(11) NOT NULL AUTO_INCREMENT,
  `DEPARTMENT_NAME` varchar(100) NOT NULL,
  PRIMARY KEY (`DEPARTMENT_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
-- 成绩表
DROP TABLE IF EXISTS `t_score`;
CREATE TABLE `t_score` (
  `SCORE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `SCORE_LEVEL` int(11) NOT NULL,
  `COURSE_ID` varchar(100) NOT NULL,
  `SCORE_VALUE` float DEFAULT NULL,
  PRIMARY KEY (`scoreId`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
-- 学生表
DROP TABLE IF EXISTS `t_student`;
CREATE TABLE `t_student` (
  `STU_ID` int(11) NOT NULL AUTO_INCREMENT,
  `STU_NO` int(11) NOT NULL,
  `STU_NAME` varchar(20) NOT NULL,
  `STU_DEPART_ID` varchar(100) DEFAULT NULL COMMENT "院系ID",
  `STU_CLASS_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`STU_ID`),
  UNIQUE KEY `UNK_STU_NO` (`UNK_STU_NO`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
-- 教师表
DROP TABLE IF EXISTS `t_teacher`;
CREATE TABLE `t_teacher` (
  `TEACHER_ID` int(11) NOT NULL AUTO_INCREMENT,
  `TEACHER_NO` varchar(16) NOT NULL,
  `TEACHER_SEX` char(2) DEFAULT "男",
  `TEACHER_NAME` varchar(20) NOT NULL,
  `TEACHER_DEPART_NO` varchar(100) DEFAULT NULL COMMENT "院系ID",
  `TITLE` varchar(32) DEFAULT "" COMMENT "职称"
  PRIMARY KEY (`TEACHER_ID`),
  UNIQUE KEY `UNK_TEACHER_NO` (`TEACHER_NO`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

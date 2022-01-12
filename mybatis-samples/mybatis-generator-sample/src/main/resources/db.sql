-- 数据库需要有以下信息：统一32位UUID作为ID，16唯一编号
-- 学生：学号、姓名、性别、年龄、选修课程名、院系名称
-- 课程：课程编号、课程名、开课院系、课程时长、任课教师编号
-- 教师：教师号、姓名、性别、职称、所在院系
-- 院系：院系名称、院系联系电话
-- 
-- 公寓：
-- 上述实体中存在如下联系：
-- （1）一个学生可选修多门课程，一门课程可被多个学生选修。
-- （2）一个教师可讲授多门课程，一门课程可由多个教师讲授。
-- （3）一个单位可有多个教师，一个教师只能属于一个单位。

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
  `DEPART_NO` varchar(100) DEFAULT NULL COMMENT "课程所开设院系编号",
  PRIMARY KEY (`COURSE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
-- 院系表
DROP TABLE IF EXISTS `t_department`;
CREATE TABLE `t_department` (
  `DEPART_ID` int(11) NOT NULL AUTO_INCREMENT,
  `DEPART_NO` varchar(11) NOT NULL AUTO_INCREMENT,
  `DEPART_NAME` varchar(100) NOT NULL,
  `TELE_PHONE` varchar(100) NOT NULL,
  PRIMARY KEY (`DEPART_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
-- 成绩表
DROP TABLE IF EXISTS `t_score`;
CREATE TABLE `t_score` (
  `SCORE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `SCORE_LEVEL` int(11) NOT NULL,
  `COURSE_NO` varchar(100) NOT NULL,
  `COURSE_NAME` varchar(100) NOT NULL,
  `SCORE_VALUE` float DEFAULT NULL,
  `TEACHER_NO` varchar(100) NOT NULL,
  PRIMARY KEY (`SCORE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
-- 学生表
DROP TABLE IF EXISTS `t_student`;
CREATE TABLE `t_student` (
  `STU_ID` int(11) NOT NULL AUTO_INCREMENT,
  `STU_NO` int(11) NOT NULL,
  `STU_SEX` char(2) DEFAULT "男",
  `STU_NAME` varchar(20) NOT NULL,
  `NATIVE_PLACE` varchar(32) COMMENT "籍贯",
  `STU_DEPART_NO` varchar(100) DEFAULT NULL COMMENT "院系ID",
  `STU_CLASS_NO` int(11) DEFAULT NULL,
  PRIMARY KEY (`STU_ID`),
  UNIQUE KEY `UNK_STU_NO` (`UNK_STU_NO`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
-- 教师表
DROP TABLE IF EXISTS `t_teacher`;
CREATE TABLE `t_teacher` (
  `TEACHER_ID` int(11) NOT NULL AUTO_INCREMENT,
  `TEACHER_NO` varchar(16) NOT NULL,
  `TEACHER_SEX` char(2) DEFAULT "男",
  `NATIVE_PLACE` varchar(32) COMMENT "籍贯",
  `TEACHER_NAME` varchar(20) NOT NULL,
  `TEACHER_DEPART_NO` varchar(100) DEFAULT NULL COMMENT "院系ID",
  `TITLE` varchar(32) DEFAULT "" COMMENT "职称",
  `TELE_PHONE` varchar(32) DEFAULT "",
  PRIMARY KEY (`TEACHER_ID`),
  UNIQUE KEY `UNK_TEACHER_NO` (`TEACHER_NO`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;























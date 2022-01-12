-- 创建数据库
CREATE
DATABASE IF NOT EXISTS `mybatis_learn` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
--- 数据库需要有以下信息：统一32位UUID作为ID，16唯一编号
-- 学生：学号、姓名、性别、年龄、选修课程名、院系名称
-- 课程：课程编号、课程名、开课院系、课程时长、任课教师编号
-- 教师：教师号、姓名、性别、职称、所在院系
-- 院系：院系名称、院系联系电话
--
-- 公寓：
-- 上述实体中存在如下联系：
-- （1）一个学生可选修多门课程，一门课程可被多个学生选修。
-- （2）一个教师可讲授多门课程，一门课程可由多个教师讲授
-- （3）一个单位可有多个教师，一个教师只能属于一个院系

SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_class
-- ----------------------------
DROP TABLE IF EXISTS `t_class`;
CREATE TABLE `t_class`
(
    `CLASS_ID`    varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `CLASS_NO`    varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `CLASS_NAME`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '未知班级',
    `DEPART_NO`   varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
    `DEPART_NAME` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
    PRIMARY KEY (`CLASS_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_cource
-- ----------------------------
DROP TABLE IF EXISTS `t_cource`;
CREATE TABLE `t_cource`
(
    `COURSE_ID`        varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `COURSE_NO`        varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
    `COURSE_NAME`      varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `COURSE_TIME_LONG` float NULL DEFAULT NULL COMMENT '课程时长',
    `TEACHER_NO`       varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `DEPART_NO`        varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程所开设院系编号',
    PRIMARY KEY (`COURSE_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_department
-- ----------------------------
DROP TABLE IF EXISTS `t_department`;
CREATE TABLE `t_department`
(
    `DEPART_ID`   varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `DEPART_NO`   varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `DEPART_NAME` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    `TELE_PHONE`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    PRIMARY KEY (`DEPART_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_score
-- ----------------------------
DROP TABLE IF EXISTS `t_score`;
CREATE TABLE `t_score`
(
    `SCORE_ID`    int(11) NOT NULL,
    `SCORE_LEVEL` int(11) NOT NULL,
    `COURSE_NO`   varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `COURSE_NAME` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `SCORE_VALUE` float NULL DEFAULT NULL,
    `TEACHER_NO`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    PRIMARY KEY (`SCORE_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_student
-- ----------------------------
DROP TABLE IF EXISTS `t_student`;
CREATE TABLE `t_student`
(
    `STU_ID`        int(11) NOT NULL,
    `STU_NO`        int(11) NOT NULL,
    `STU_SEX`       char(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '男',
    `STU_NAME`      varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
    `NATIVE_PLACE`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '籍贯',
    `STU_DEPART_NO` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '院系ID',
    `STU_CLASS_NO`  int(11) NULL DEFAULT NULL,
    PRIMARY KEY (`STU_ID`) USING BTREE,
    UNIQUE INDEX `UNK_STU_NO`(`STU_NO`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_teacher
-- ----------------------------
DROP TABLE IF EXISTS `t_teacher`;
CREATE TABLE `t_teacher`
(
    `TEACHER_ID`        int(11) NOT NULL,
    `TEACHER_NO`        varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
    `TEACHER_SEX`       char(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '男',
    `NATIVE_PLACE`      varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '籍贯',
    `TEACHER_NAME`      varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
    `TEACHER_DEPART_NO` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '院系ID',
    `TITLE`             varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '职称',
    `TELE_PHONE`        varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
    PRIMARY KEY (`TEACHER_ID`) USING BTREE,
    UNIQUE INDEX `UNK_TEACHER_NO`(`TEACHER_NO`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET
FOREIGN_KEY_CHECKS = 1;

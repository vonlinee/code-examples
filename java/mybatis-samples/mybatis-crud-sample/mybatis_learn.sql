-- 数据库需要有以下信息：统一32位UUID作为ID，16唯一编号
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

DROP TABLE IF EXISTS `admin_class`;
CREATE TABLE `admin_class`
(
    `class_id`   varchar(36) NOT NULL COMMENT '班级ID，主键',
    `class_no`   varchar(16) NOT NULL COMMENT '班级编号',
    `class_name` varchar(50) DEFAULT NULL,
    `grade`      char(2) COMMENT '年级代码',
    PRIMARY KEY (`class_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT '行政班信息表';

INSERT INTO admin_class VALUE (uuid(), 'C001', '三年级1班', 'G3');
INSERT INTO admin_class VALUE (uuid(), 'C002', '三年级2班', 'G3');
INSERT INTO admin_class VALUE (uuid(), 'C003', '三年级3班', 'G3');
INSERT INTO admin_class VALUE (uuid(), 'C004', '三年级4班', 'G3');

DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`
(
    `course_id`   varchar(36) NOT NULL COMMENT '课程ID,主键',
    `course_no`   varchar(36) NOT NULL COMMENT '课程编号',
    `course_name` varchar(100) DEFAULT NULL COMMENT '课程名称',
    `credit`      tinyint null comment '学分',
    PRIMARY KEY (`course_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT '课程信息表';

DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`
(
    `stu_id`       varchar(36) NOT NULL COMMENT '学生ID,主键',
    `stu_no`       varchar(36) NOT NULL COMMENT '学号',
    `sex`          char(2) COMMENT '性别',
    `name`         varchar(20) DEFAULT '' COMMENT '名称',
    `birthday`     datetime COMMENT '出生日期',
    `belong_class` varchar(32) DEFAULT NULL COMMENT '所属班级',
    PRIMARY KEY (`stu_id`) USING BTREE,
    UNIQUE KEY `unk_stu_no` (`stu_no`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT '学生信息表';

DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`
(
    `teacher_id` int(36) NOT NULL COMMENT '教师ID,主键',
    `teacher_no` varchar(36) NOT NULL COMMENT '教师编号',
    `sex`        char(2) COMMENT '性别',
    title        varchar(12) null comment '职称',
    `name`       varchar(20) DEFAULT '' COMMENT '名称',
    PRIMARY KEY (`teacher_id`) USING BTREE,
    UNIQUE KEY `unk_teacher_no` (`teacher_no`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT '教师信息表';
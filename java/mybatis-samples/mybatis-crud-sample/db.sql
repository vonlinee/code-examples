-- mybatis_learn.t_class definition

CREATE TABLE `t_class` (
  `CLASS_ID` varchar(36) NOT NULL,
  `CLASS_NO` varchar(16) NOT NULL,
  `CLASS_NAME` varchar(50) DEFAULT '未知班级',
  `DEPART_NO` varchar(100) DEFAULT '',
  `DEPART_NAME` varchar(100) DEFAULT '',
  PRIMARY KEY (`CLASS_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;


-- mybatis_learn.t_cource definition

CREATE TABLE `t_cource` (
  `COURSE_ID` varchar(36) NOT NULL,
  `COURSE_NO` varchar(36) DEFAULT '',
  `COURSE_NAME` varchar(100) DEFAULT NULL,
  `COURSE_TIME_LONG` float DEFAULT NULL COMMENT '课程时长',
  `TEACHER_NO` varchar(100) DEFAULT NULL,
  `DEPART_NO` varchar(100) DEFAULT NULL COMMENT '课程所开设院系编号',
  PRIMARY KEY (`COURSE_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;


-- mybatis_learn.t_department definition

CREATE TABLE `t_department` (
  `DEPART_ID` varchar(36) NOT NULL,
  `DEPART_NO` varchar(11) DEFAULT NULL,
  `DEPART_NAME` varchar(100) DEFAULT NULL,
  `TELE_PHONE` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`DEPART_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;


-- mybatis_learn.t_score definition

CREATE TABLE `t_score` (
  `SCORE_ID` varchar(36) NOT NULL,
  `SCORE_LEVEL` varchar(16) NOT NULL,
  `COURSE_NO` varchar(100) NOT NULL,
  `COURSE_NAME` varchar(100) NOT NULL,
  `SCORE_VALUE` float DEFAULT NULL,
  `TEACHER_NO` varchar(100) NOT NULL,
  PRIMARY KEY (`SCORE_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;


-- mybatis_learn.t_student definition

CREATE TABLE `t_student` (
  `STU_ID` varchar(36) NOT NULL,
  `STU_NO` varchar(36) NOT NULL,
  `STU_SEX` char(2) DEFAULT '男',
  `STU_AGE` int(3) DEFAULT NULL,
  `STU_NAME` varchar(20) DEFAULT '',
  `NATIVE_PLACE` varchar(32) DEFAULT NULL COMMENT '籍贯',
  `STU_DEPART_NO` varchar(100) DEFAULT NULL COMMENT '院系ID',
  `STU_CLASS_NO` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`STU_ID`) USING BTREE,
  UNIQUE KEY `UNK_STU_NO` (`STU_NO`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;


-- mybatis_learn.t_teacher definition

CREATE TABLE `t_teacher` (
  `TEACHER_ID` varchar(36) NOT NULL,
  `TEACHER_NO` varchar(36) DEFAULT '',
  `TEACHER_SEX` char(2) DEFAULT '男',
  `NATIVE_PLACE` varchar(32) DEFAULT NULL COMMENT '籍贯',
  `TEACHER_NAME` varchar(20) DEFAULT '',
  `DEPART_NO` varchar(100) DEFAULT '' COMMENT '院系ID',
  `DEPART_NAME` varchar(100) DEFAULT NULL,
  `TITLE` varchar(32) DEFAULT '' COMMENT '职称',
  `TELE_PHONE` varchar(32) DEFAULT '',
  PRIMARY KEY (`TEACHER_ID`) USING BTREE,
  UNIQUE KEY `UNK_TEACHER_NO` (`TEACHER_NO`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;


-- mybatis_learn.t_person_infomation definition

CREATE TABLE `t_person_infomation` (
  `PERSON_ID` varchar(36) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;





INSERT INTO mybatis_learn.t_class (CLASS_ID,CLASS_NO,CLASS_NAME,DEPART_NO,DEPART_NAME) VALUES
   ('51055b86-7419-11ec-9b88-38142830461b','02111709','9班','',''),
   ('800c1fcd-7419-11ec-9b88-38142830461b','02111708','8班','','');


INSERT INTO mybatis_learn.t_cource (COURSE_ID,COURSE_NO,COURSE_NAME,COURSE_TIME_LONG,TEACHER_NO,DEPART_NO) VALUES
   ('78bf15f5-94a3-11ec-afdd-38142830461b','3-105','计算机导论',40.0,'T0001',''),
   ('7af8142b-94a3-11ec-afdd-38142830461b','3-245','操作系统',40.0,'T0001',''),
   ('7cd0fc68-94a3-11ec-afdd-38142830461b','6-166','数字电路',40.0,'T0003',''),
   ('7e5074f1-94a3-11ec-afdd-38142830461b','9-888','高等数学',40.0,'T0004','');


INSERT INTO mybatis_learn.t_department (DEPART_ID,DEPART_NO,DEPART_NAME,TELE_PHONE) VALUES
   ('05f2cf64-741f-11ec-9b88-38142830461b','D0001','光电工程学院','020-1213212'),
   ('05f33973-741f-11ec-9b88-38142830461b','D0002','传媒学院','020-1213212'),
   ('05f38605-741f-11ec-9b88-38142830461b','D0003','理学院','020-1213212'),
   ('05f3d35f-741f-11ec-9b88-38142830461b','D0004','网络与信息安全学院','020-1213212');


INSERT INTO mybatis_learn.t_student (STU_ID,STU_NO,STU_SEX,STU_AGE,STU_NAME,NATIVE_PLACE,STU_DEPART_NO,STU_CLASS_NO) VALUES
   ('64880d9d-741a-11ec-9b88-38142830461b','S001','男',22,'李彤','中国','','C001'),
   ('7b82b625-741a-11ec-9b88-38142830461b','S002','女',18,'许茗','中国','','C002'),
   ('7b838d7a-741a-11ec-9b88-38142830461b','S003','女',17,'张梦华','中国','','C003'),
   ('7b83d2b7-741a-11ec-9b88-38142830461b','S004','女',25,'林雪','中国','','C004'),
   ('7b841298-741a-11ec-9b88-38142830461b','S005','女',24,'孟圆圆','中国','','C005'),
   ('7b844e92-741a-11ec-9b88-38142830461b','S006','男',15,'罗城','中国','','C006');


INSERT INTO mybatis_learn.t_teacher (TEACHER_ID,TEACHER_NO,TEACHER_SEX,NATIVE_PLACE,TEACHER_NAME,DEPART_NO,DEPART_NAME,TITLE,TELE_PHONE) VALUES
   ('8836be69-741e-11ec-9b88-38142830461b','T0001','男','广东省广州市','杨凌','1','计算机网络学院','讲师','17347898125'),
   ('8837b7a2-741e-11ec-9b88-38142830461b','T0002','女','广东省广州市','林春梅','1','网络与信息安全学院','教授','17347898125'),
   ('883801ba-741e-11ec-9b88-38142830461b','T0003','女','广东省广州市','张霞','1','理学院','讲师','17347898125'),
   ('883853b1-741e-11ec-9b88-38142830461b','T0004','女','广东省广州市','李玉梅','1','传媒学院','副教授','17347898125');

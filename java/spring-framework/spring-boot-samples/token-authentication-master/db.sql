DROP TABLE IF EXISTS db_auth_token;
CREATE DATABASE `db_auth_token` DEFAULT CHARACTER SET utf8mb4;
USE `db_auth_token`;
-- 部门表
CREATE TABLE `t_dept`
(
    DEPT_ID   INT PRIMARY KEY AUTO_INCREMENT COMMENT '部门ID',
    DEPT_NAME VARCHAR(50)  COMMENT '部门名称'
);
-- 用户表
CREATE TABLE `t_user`
(
    USER_ID       int PRIMARY KEY AUTO_INCREMENT COMMENT '用户id',
    USER_NAME varchar(50) COMMENT '登陆名', 
    USER_PASSWORD varchar(50) COMMENT '密码',
    USER_PHONE    varchar(11) COMMENT '手机号码',
    GENDER      int  COMMENT '性别',
    AGE      int COMMENT '年龄', 
    DEPT_ID      int
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- 角色表 记录系统中的角色信息
CREATE TABLE `t_role`
(
    ROLE_ID   int PRIMARY KEY AUTO_INCREMENT COMMENT '角色id', -- 
    ROLE_NAME varchar(255)  COMMENT '角色名称'                    -- 
)ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- 菜单表 记录系统中所有的菜单信息 精确到按钮级别
CREATE TABLE `t_menu`
(
    MENU_ID       int PRIMARY KEY AUTO_INCREMENT COMMENT '权限ID'        , -- 
    MENU_NAME     varchar(255) COMMENT '权限名称'  ,                   -- 
    URL varchar(255) COMMENT '当前权限所访问的系统中的资源地址' ,                   -- 
    PARENT_MENU_ID      int COMMENT '记录权限的父级权限编号',            -- 
    LEVEL    int  COMMENT '记录权限级别 （1：一级菜单 2：二级菜单，3：按钮）' -- 
)ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- 用户角色表 记录系统中的用户所拥有的角色信息 用户角色关系
CREATE TABLE `t_user_role_relation`
(
    ID  int PRIMARY KEY AUTO_INCREMENT COMMENT '主键', -- 
    USER_ID int COMMENT '用户id',-- 
    ROLE_ID int COMMENT '角色id'  -- 
)ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='用户角色表';

-- 部门权限表
CREATE TABLE `t_dept_permission_relation`
(
    ID  int PRIMARY KEY AUTO_INCREMENT COMMENT '主键', -- 主键
    DEPT_ID int COMMENT '部门编号',                            -- 
    MENU_ID int   COMMENT '菜单编号'                           -- 
)ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='部门权限表';

-- 用户权限表
CREATE TABLE `t_user_permission_relation`
(
    ID  int PRIMARY KEY AUTO_INCREMENT COMMENT '主键', -- 主键
    USER_ID int COMMENT '用户编号',                            -- 
    MENU_ID int  COMMENT '菜单编号'                            -- 
)ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='用户权限表';

-- 角色权限表
CREATE TABLE `t_user_role_permission`
(
    ID  int PRIMARY KEY AUTO_INCREMENT COMMENT '主键', -- 
    ROLE_ID int COMMENT '角色编号',                            -- 
    MENU_ID int   COMMENT '菜单编号'                           -- 
)ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='角色权限表';


-- 基础数据录入
-- 录入部门信息
INSERT INTO `t_dept` VALUES (NULL, '教学部'); -- 教学部部门编号1
INSERT INTO t_dept VALUES (NULL,
        '财务部');
-- 财务部部门编号2
-- 录入用户信息
INSERT INTO `t_user` VALUES (NULL,
        'qiang',
        '123456',
        '13666666666',
        1,
        18,
        1);
-- qiang的编号1
INSERT
INTO `t_user`
VALUES (NULL,
        'cong',
        '123456',
        '13888888888',
        1,
        18,
        2);
-- cong的编号2
-- 录入菜单信息
INSERT
INTO t_menu
VALUES (NULL,
        '教学管理',
        '',
        0,
        1);
-- 菜单编号1 无父级菜单 一级菜单
INSERT
INTO t_menu
VALUES (NULL,
        '课程管理',
        '',
        1,
        2);
-- 菜单编号2 父级菜单编号1 二级菜单
INSERT
INTO t_menu
VALUES (NULL,
        '新增课程',
        '',
        2,
        3);
-- 菜单编号3 父级菜单编号2 按钮
INSERT
INTO t_menu
VALUES (NULL,
        '删除课程',
        '',
        2,
        3);
-- 菜单编号4 父级菜单编号2 按钮
INSERT
INTO t_menu
VALUES (NULL,
        '修改课程',
        '',
        2,
        3);
-- 菜单编号5 父级菜单编号2 按钮
INSERT
INTO t_menu
VALUES (NULL,
        '财务管理',
        '',
        0,
        1);
-- 菜单编号6 无父级菜单 一级菜单
INSERT
INTO t_menu
VALUES (NULL,
        '报销管理',
        '',
        6,
        2);
-- 菜单编号7 父级菜单6  二级菜单
INSERT
INTO t_menu
VALUES (NULL,
        '审核报销',
        '',
        7,
        3);
-- 菜单编号8 父级菜单7  按钮
INSERT
INTO t_menu
VALUES (NULL,
        '申请报销',
        '',
        6,
        2);
-- 菜单编号9 父级菜单6  二级菜单
INSERT
INTO t_menu
VALUES (NULL,
        '撤回',
        '',
        9,
        3);
-- 菜单编号10 父级菜单9  按钮
INSERT
INTO t_menu
VALUES (NULL,
        '系统管理',
        '',
        0,
        1);
-- 菜单编号11 无父级菜单 一级菜单
INSERT
INTO t_menu
VALUES (NULL,
        '部门权限管理',
        '',
        11,
        2);
-- 菜单编号12 父级菜单11 二级菜单
INSERT
INTO t_menu
VALUES (NULL,
        '角色权限管理',
        '',
        11,
        2);
-- 菜单编号13 父级菜单11 二级菜单
INSERT
INTO t_menu
VALUES (NULL,
        '用户权限管理',
        '',
        11,
        2);
-- 菜单编号14 父级菜单11 二级菜单
INSERT
INTO t_menu
VALUES (NULL,
        '变更角色权限',
        '',
        13,
        3);
-- 菜单编号15 父级菜单13 按钮
-- 录入角色信息
INSERT
INTO t_role
VALUES (NULL,
        '系统管理员');
-- 角色编号1
INSERT
INTO t_role
VALUES (NULL,
        '总经理');
-- 角色编号2
INSERT
INTO t_role
VALUES (NULL,
        '部门经理');
-- 角色编号3
-- 录入用户角色信息
INSERT
INTO t_user_role_relation
VALUES (NULL,
        1,
        1);
-- 用户编号1的qiang为管理员角色
INSERT
INTO t_user_role_relation
VALUES (NULL,
        2,
        2);

INSERT
INTO t_user_role_relation
VALUES (NULL,
        2,
        3);
-- 用户编号2的cong为总经理兼部门经理
-- 录入部门权限
INSERT
INTO t_dept_permission_relation
VALUES (NULL,
        1,
        1);
-- 教学部门拥有教学管理菜单
INSERT
INTO t_dept_permission_relation
VALUES (NULL,
        1,
        2);
-- 教学部门拥有教学管理菜单下的课程管理（查询）
INSERT
INTO t_dept_permission_relation
VALUES (NULL,
        1,
        6);
-- 教学部门拥有财务管理菜单
INSERT
INTO t_dept_permission_relation
VALUES (NULL,
        1,
        9);
-- 教学部门拥有财务管理菜单下的申请报销
INSERT
INTO t_dept_permission_relation
VALUES (NULL,
        2,
        6);
-- 财务部门拥有财务管理菜单
INSERT
INTO t_dept_permission_relation
VALUES (NULL,
        2,
        7);
-- 财务部门拥有财务管理菜单下的报销管理
INSERT
INTO t_dept_permission_relation
VALUES (NULL,
        2,
        8);
-- 财务部门拥有财务管理菜单下的报销管理（审核报销）
INSERT
INTO t_dept_permission_relation
VALUES (NULL,
        2,
        9);
-- 财务部门拥有财务管理菜单下的申请报销
-- 录入角色权限
INSERT
INTO t_user_role_permission
VALUES (NULL,
        1,
        1);

INSERT
INTO t_user_role_permission
VALUES (NULL,
        1,
        2);

INSERT
INTO t_user_role_permission
VALUES (NULL,
        1,
        3);

INSERT
INTO t_user_role_permission
VALUES (NULL,
        1,
        4);

INSERT
INTO t_user_role_permission
VALUES (NULL,
        1,
        5);

INSERT
INTO t_user_role_permission
VALUES (NULL,
        1,
        6);

INSERT
INTO t_user_role_permission
VALUES (NULL,
        1,
        7);

INSERT
INTO t_user_role_permission
VALUES (NULL,
        1,
        8);

INSERT
INTO t_user_role_permission
VALUES (NULL,
        1,
        9);

INSERT
INTO t_user_role_permission
VALUES (NULL,
        1,
        10);

INSERT
INTO t_user_role_permission
VALUES (NULL,
        1,
        11);

INSERT
INTO t_user_role_permission
VALUES (NULL,
        1,
        12);

INSERT
INTO t_user_role_permission
VALUES (NULL,
        1,
        13);

INSERT
INTO t_user_role_permission
VALUES (NULL,
        1,
        14);

INSERT
INTO t_user_role_permission
VALUES (NULL,
        1,
        15);

INSERT
INTO t_user_role_permission
VALUES (NULL,
        2,
        1);

INSERT
INTO t_user_role_permission
VALUES (NULL,
        2,
        2);

INSERT
INTO t_user_role_permission
VALUES (NULL,
        2,
        3);

INSERT
INTO t_user_role_permission
VALUES (NULL,
        2,
        4);

INSERT
INTO t_user_role_permission
VALUES (NULL,
        2,
        5);

INSERT
INTO t_user_role_permission
VALUES (NULL,
        2,
        6);

INSERT
INTO t_user_role_permission
VALUES (NULL,
        2,
        7);

INSERT
INTO t_user_role_permission
VALUES (NULL,
        2,
        8);

INSERT
INTO t_user_role_permission
VALUES (NULL,
        2,
        9);

INSERT
INTO t_user_role_permission
VALUES (NULL,
        2,
        10);

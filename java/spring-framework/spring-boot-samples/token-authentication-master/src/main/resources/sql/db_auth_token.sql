/*
 Navicat Premium Data Transfer

 Source Server         : localshots_mysql
 Source Server Type    : MySQL
 Source Server Version : 50736
 Source Host           : localhost:3306
 Source Schema         : db_auth_token

 Target Server Type    : MySQL
 Target Server Version : 50736
 File Encoding         : 65001

 Date: 03/05/2022 18:02:47
*/

CREATE DATABASE `db_auth_token` DEFAULT CHARACTER SET utf8mb4;
DROP TABLE IF EXISTS `db_auth_token`;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_dept
-- ----------------------------
DROP TABLE IF EXISTS `t_dept`;
CREATE TABLE `t_dept`  (
  `DEPT_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '部门ID',
  `DEPT_NAME` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部门名称',
  PRIMARY KEY (`DEPT_ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_dept
-- ----------------------------
INSERT INTO `t_dept` VALUES (1, '教学部');
INSERT INTO `t_dept` VALUES (2, '财务部');

-- ----------------------------
-- Table structure for t_dept_permission_relation
-- ----------------------------
DROP TABLE IF EXISTS `t_dept_permission_relation`;
CREATE TABLE `t_dept_permission_relation`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `DEPT_ID` int(11) NULL DEFAULT NULL COMMENT '部门编号',
  `MENU_ID` int(11) NULL DEFAULT NULL COMMENT '菜单编号',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '部门权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_dept_permission_relation
-- ----------------------------
INSERT INTO `t_dept_permission_relation` VALUES (22, 1, 1);
INSERT INTO `t_dept_permission_relation` VALUES (23, 1, 2);
INSERT INTO `t_dept_permission_relation` VALUES (24, 1, 6);
INSERT INTO `t_dept_permission_relation` VALUES (25, 1, 9);
INSERT INTO `t_dept_permission_relation` VALUES (26, 2, 6);
INSERT INTO `t_dept_permission_relation` VALUES (27, 2, 7);
INSERT INTO `t_dept_permission_relation` VALUES (28, 2, 8);
INSERT INTO `t_dept_permission_relation` VALUES (29, 2, 9);

-- ----------------------------
-- Table structure for t_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE `t_menu`  (
  `MENU_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `MENU_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限名称',
  `URL` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '当前权限所访问的系统中的资源地址',
  `PARENT_MENU_ID` int(11) NULL DEFAULT NULL COMMENT '记录权限的父级权限编号',
  `LEVEL` int(11) NULL DEFAULT NULL COMMENT '记录权限级别 （1：一级菜单 2：二级菜单，3：按钮）',
  PRIMARY KEY (`MENU_ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 37 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_menu
-- ----------------------------
INSERT INTO `t_menu` VALUES (22, '教学管理', '', 0, 1);
INSERT INTO `t_menu` VALUES (23, '课程管理', '', 1, 2);
INSERT INTO `t_menu` VALUES (24, '新增课程', '', 2, 3);
INSERT INTO `t_menu` VALUES (25, '删除课程', '', 2, 3);
INSERT INTO `t_menu` VALUES (26, '修改课程', '', 2, 3);
INSERT INTO `t_menu` VALUES (27, '财务管理', '', 0, 1);
INSERT INTO `t_menu` VALUES (28, '报销管理', '', 6, 2);
INSERT INTO `t_menu` VALUES (29, '审核报销', '', 7, 3);
INSERT INTO `t_menu` VALUES (30, '申请报销', '', 6, 2);
INSERT INTO `t_menu` VALUES (31, '撤回', '', 9, 3);
INSERT INTO `t_menu` VALUES (32, '系统管理', '', 0, 1);
INSERT INTO `t_menu` VALUES (33, '部门权限管理', '', 11, 2);
INSERT INTO `t_menu` VALUES (34, '角色权限管理', '', 11, 2);
INSERT INTO `t_menu` VALUES (35, '用户权限管理', '', 11, 2);
INSERT INTO `t_menu` VALUES (36, '变更角色权限', '', 13, 3);

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role`  (
  `ROLE_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `ROLE_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  PRIMARY KEY (`ROLE_ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES (22, '系统管理员');
INSERT INTO `t_role` VALUES (23, '总经理');
INSERT INTO `t_role` VALUES (24, '部门经理');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `USER_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `USER_NAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登陆名',
  `USER_PASSWORD` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `USER_PHONE` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `GENDER` int(11) NULL DEFAULT NULL COMMENT '性别',
  `AGE` int(11) NULL DEFAULT NULL COMMENT '年龄',
  `DEPT_ID` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`USER_ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (22, 'qiang', '123456', '13666666666', 1, 18, 1);
INSERT INTO `t_user` VALUES (23, 'cong', '123456', '13888888888', 1, 18, 2);

-- ----------------------------
-- Table structure for t_user_permission_relation
-- ----------------------------
DROP TABLE IF EXISTS `t_user_permission_relation`;
CREATE TABLE `t_user_permission_relation`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `USER_ID` int(11) NULL DEFAULT NULL COMMENT '用户编号',
  `MENU_ID` int(11) NULL DEFAULT NULL COMMENT '菜单编号',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role_permission`;
CREATE TABLE `t_user_role_permission`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ROLE_ID` int(11) NULL DEFAULT NULL COMMENT '角色编号',
  `MENU_ID` int(11) NULL DEFAULT NULL COMMENT '菜单编号',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 47 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user_role_permission
-- ----------------------------
INSERT INTO `t_user_role_permission` VALUES (22, 1, 1);
INSERT INTO `t_user_role_permission` VALUES (23, 1, 2);
INSERT INTO `t_user_role_permission` VALUES (24, 1, 3);
INSERT INTO `t_user_role_permission` VALUES (25, 1, 4);
INSERT INTO `t_user_role_permission` VALUES (26, 1, 5);
INSERT INTO `t_user_role_permission` VALUES (27, 1, 6);
INSERT INTO `t_user_role_permission` VALUES (28, 1, 7);
INSERT INTO `t_user_role_permission` VALUES (29, 1, 8);
INSERT INTO `t_user_role_permission` VALUES (30, 1, 9);
INSERT INTO `t_user_role_permission` VALUES (31, 1, 10);
INSERT INTO `t_user_role_permission` VALUES (32, 1, 11);
INSERT INTO `t_user_role_permission` VALUES (33, 1, 12);
INSERT INTO `t_user_role_permission` VALUES (34, 1, 13);
INSERT INTO `t_user_role_permission` VALUES (35, 1, 14);
INSERT INTO `t_user_role_permission` VALUES (36, 1, 15);
INSERT INTO `t_user_role_permission` VALUES (37, 2, 1);
INSERT INTO `t_user_role_permission` VALUES (38, 2, 2);
INSERT INTO `t_user_role_permission` VALUES (39, 2, 3);
INSERT INTO `t_user_role_permission` VALUES (40, 2, 4);
INSERT INTO `t_user_role_permission` VALUES (41, 2, 5);
INSERT INTO `t_user_role_permission` VALUES (42, 2, 6);
INSERT INTO `t_user_role_permission` VALUES (43, 2, 7);
INSERT INTO `t_user_role_permission` VALUES (44, 2, 8);
INSERT INTO `t_user_role_permission` VALUES (45, 2, 9);
INSERT INTO `t_user_role_permission` VALUES (46, 2, 10);

-- ----------------------------
-- Table structure for t_user_role_relation
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role_relation`;
CREATE TABLE `t_user_role_relation`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `USER_ID` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `ROLE_ID` int(11) NULL DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user_role_relation
-- ----------------------------
INSERT INTO `t_user_role_relation` VALUES (22, 1, 1);
INSERT INTO `t_user_role_relation` VALUES (23, 2, 2);
INSERT INTO `t_user_role_relation` VALUES (24, 2, 3);

SET FOREIGN_KEY_CHECKS = 1;

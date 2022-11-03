SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sec_permission
-- ----------------------------
DROP TABLE IF EXISTS `sec_permission`;
CREATE TABLE `sec_permission`  (
  `id` bigint NOT NULL COMMENT '主键',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限名',
  `url` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型为页面时，代表前端路由地址，类型为按钮时，代表后端接口地址',
  `type` int NOT NULL COMMENT '权限类型，页面-1，按钮-2',
  `permission` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限表达式',
  `method` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '后端接口访问方式',
  `sort` int NOT NULL COMMENT '排序',
  `parent_id` bigint NOT NULL COMMENT '父级id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sec_permission
-- ----------------------------
INSERT INTO `sec_permission` VALUES (1072806379288399872, '测试页面', '/test', 1, 'page:test', NULL, 1, 0);
INSERT INTO `sec_permission` VALUES (1072806379313565696, '测试页面-查询', '/**/test', 2, 'btn:test:query', 'GET', 1, 1072806379288399872);
INSERT INTO `sec_permission` VALUES (1072806379330342912, '测试页面-添加', '/**/test', 2, 'btn:test:insert', 'POST', 2, 1072806379288399872);
INSERT INTO `sec_permission` VALUES (1072806379342925824, '监控在线用户页面', '/monitor', 1, 'page:monitor:online', NULL, 2, 0);
INSERT INTO `sec_permission` VALUES (1072806379363897344, '在线用户页面-查询', '/**/api/monitor/online/user', 2, 'btn:monitor:online:query', 'GET', 1, 1072806379342925824);
INSERT INTO `sec_permission` VALUES (1072806379384868864, '在线用户页面-踢出', '/**/api/monitor/online/user/kickout', 2, 'btn:monitor:online:kickout', 'DELETE', 2, 1072806379342925824);

-- ----------------------------
-- Table structure for sec_role
-- ----------------------------
DROP TABLE IF EXISTS `sec_role`;
CREATE TABLE `sec_role`  (
  `id` bigint NOT NULL COMMENT '主键',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名',
  `description` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `create_time` date NOT NULL COMMENT '创建时间',
  `update_time` date NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sec_role
-- ----------------------------
INSERT INTO `sec_role` VALUES (1072806379208708096, '管理员', '超级管理员', '2021-08-22', '2021-08-22');
INSERT INTO `sec_role` VALUES (1072806379238068224, '普通用户', '普通用户', '2021-08-22', '2021-08-22');

-- ----------------------------
-- Table structure for sec_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sec_role_permission`;
CREATE TABLE `sec_role_permission`  (
  `role_id` bigint NOT NULL COMMENT '角色主键',
  `permission_id` bigint NOT NULL COMMENT '权限主键',
  PRIMARY KEY (`role_id`, `permission_id`) USING BTREE,
  INDEX `FKpin61ltb1uniw17shihq1cove`(`permission_id`) USING BTREE,
  CONSTRAINT `FK2btquksd4tgtj9a8pn6qyeubl` FOREIGN KEY (`role_id`) REFERENCES `sec_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKpin61ltb1uniw17shihq1cove` FOREIGN KEY (`permission_id`) REFERENCES `sec_permission` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色权限关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sec_role_permission
-- ----------------------------
INSERT INTO `sec_role_permission` VALUES (1072806379208708096, 1072806379288399872);
INSERT INTO `sec_role_permission` VALUES (1072806379238068224, 1072806379288399872);
INSERT INTO `sec_role_permission` VALUES (1072806379208708096, 1072806379313565696);
INSERT INTO `sec_role_permission` VALUES (1072806379238068224, 1072806379313565696);
INSERT INTO `sec_role_permission` VALUES (1072806379208708096, 1072806379330342912);
INSERT INTO `sec_role_permission` VALUES (1072806379208708096, 1072806379342925824);
INSERT INTO `sec_role_permission` VALUES (1072806379208708096, 1072806379363897344);
INSERT INTO `sec_role_permission` VALUES (1072806379208708096, 1072806379384868864);

-- ----------------------------
-- Table structure for sec_user
-- ----------------------------
DROP TABLE IF EXISTS `sec_user`;
CREATE TABLE `sec_user`  (
  `id` bigint NOT NULL COMMENT '主键',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `birthday` bigint NULL DEFAULT NULL COMMENT '生日',
  `sex` int NULL DEFAULT NULL COMMENT '性别，男-1，女-2',
  `status` int NOT NULL DEFAULT 1 COMMENT '状态，启用-1，禁用-0',
  `create_time` date NOT NULL COMMENT '创建时间',
  `update_time` date NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE,
  UNIQUE INDEX `phone`(`phone`) USING BTREE,
  UNIQUE INDEX `email`(`email`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sec_user
-- ----------------------------
INSERT INTO `sec_user` VALUES (1072806377661009920, 'admin', '$2a$10$2xC9NWUJxgCWwjJtADT3mOY3uekARVMbRiXWntKZSFsSf5ugP9K2G', '管理员', '17300000000', 'admin@xkcoding.com', 785433600000, 1, 1, '2021-08-22', '2021-08-22');
INSERT INTO `sec_user` VALUES (1072806378780889088, 'user', '$2a$10$OUDl4thpcHfs7WZ1kMUOb.ZO5eD4QANW5E.cexBLiKDIzDNt87QbO', '普通用户', '17300001111', 'user@xkcoding.com', 785433600000, 1, 1, '2021-08-22', '2021-08-22');

-- ----------------------------
-- Table structure for sec_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sec_user_role`;
CREATE TABLE `sec_user_role`  (
  `user_id` bigint NOT NULL COMMENT '用户主键',
  `role_id` bigint NOT NULL COMMENT '角色主键',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE,
  INDEX `FKfowkd8vw5qarh8b8y9noaf4et`(`role_id`) USING BTREE,
  CONSTRAINT `FK835bbyiy6majrolcov7bp0yo0` FOREIGN KEY (`user_id`) REFERENCES `sec_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKfowkd8vw5qarh8b8y9noaf4et` FOREIGN KEY (`role_id`) REFERENCES `sec_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户角色关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sec_user_role
-- ----------------------------
INSERT INTO `sec_user_role` VALUES (1072806377661009920, 1072806379208708096);
INSERT INTO `sec_user_role` VALUES (1072806378780889088, 1072806379238068224);

SET FOREIGN_KEY_CHECKS = 1;

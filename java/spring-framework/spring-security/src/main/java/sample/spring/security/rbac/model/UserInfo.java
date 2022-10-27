package sample.spring.security.rbac.model;

import lombok.Data;

/**
 * CREATE TABLE `sys_user` (
 * `id` int(32) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
 * `username` varchar(64) NOT NULL DEFAULT '' COMMENT '用户名字',
 * `telephone` varchar(64) NOT NULL DEFAULT '' COMMENT '用户手机号',
 * `mail` varchar(64) NOT NULL DEFAULT '' COMMENT '用户邮箱',
 * `password` varchar(64) NOT NULL DEFAULT '' COMMENT '用户密码',
 * `remark` varchar(255) DEFAULT '' COMMENT '备注',
 * `dep_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户部门表',
 * `status` tinyint(3) NOT NULL DEFAULT '1' COMMENT '用户的状态 1 ：正常  0：冻结 2：删除',
 * `operator` varchar(64) NOT NULL DEFAULT '' COMMENT '操作人',
 * `operator_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间',
 * `operator_ip` varchar(64) NOT NULL DEFAULT '' COMMENT '操作人的ip',
 * PRIMARY KEY (`id`)
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
 */
@Data
public class UserInfo {


}

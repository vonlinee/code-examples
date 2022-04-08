/************************************************************************
SCM 供应链数据库。用于演示分库分表。
数据库清单：
  scm		主库
  scm_1	分库1
  scm_0 	分库2
表清单：
	t_tenant
			租户表。
			分库分表规则：广播表，存在于scm_1、scm_0两个分库中
	t_purchase_order
			采购单表。
			分库规则：租户ID %2；
			分表规则：租户ID + 订单日期按年分表。t_purchase_order_101_2021（其中，101为租户ID，2021为年份）
	t_purchase_item
			采购货品明细表。
			分库分表规则同 t_purchase_order
	t_operate_log
			操作日志表
			分库规则：2021年及以后的数据存主库；2021年以前的：日志时间的年份 % 2
			分表规则：2021年及以后的数据存主库不分表；2021年以前的：日志月份分表。如：t_operate_log_2010_02
	t_config
			配置表
			分库规则：广播表，存在于scm_1、scm_0两个分库中


数据库按租户ID对2取模
*************************************************************************/

-- 建库脚本
CREATE DATABASE IF NOT EXISTS `scm_0` DEFAULT CHARACTER SET utf8;
CREATE DATABASE IF NOT EXISTS `scm_1` DEFAULT CHARACTER SET utf8;
CREATE DATABASE IF NOT EXISTS `scm` DEFAULT CHARACTER SET utf8;


-- 创建租户广播表
DROP TABLE IF EXISTS `scm_0`.`t_tenant`;
CREATE TABLE `scm_0`.`t_tenant` (
  `tenant_id` BIGINT(20) NOT NULL COMMENT '租户ID',
  `tenant_name` VARCHAR(100) NOT NULL COMMENT '租户名称',
  `status` TINYINT NOT NULL DEFAULT 0 COMMENT '状态。1-启用；2-停用；',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` BIGINT(20) NOT NULL DEFAULT 0 COMMENT '创建人ID',
	`update_time` DATETIME NOT NULL ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `update_user_id` BIGINT(20) NOT NULL DEFAULT 0 COMMENT '最后更新用户ID',
  PRIMARY KEY (`tenant_id`),
	UNIQUE KEY `uk_tenant_name`(`tenant_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '租户';

DROP TABLE IF EXISTS `scm_1`.`t_tenant`;
CREATE TABLE `scm_1`.`t_tenant` (
  `tenant_id` BIGINT(20) NOT NULL COMMENT '租户ID',
  `tenant_name` VARCHAR(100) NOT NULL COMMENT '租户名称',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态。1-启用；2-停用；',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` BIGINT(20) NOT NULL DEFAULT 0 COMMENT '创建人ID',
	`update_time` DATETIME NOT NULL ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `update_user_id` BIGINT(20) NOT NULL DEFAULT 0 COMMENT '最后更新用户ID',
  PRIMARY KEY (`tenant_id`),
	UNIQUE KEY `uk_tenant_name`(`tenant_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '租户';


-- 创建配置广播表
DROP TABLE IF EXISTS `scm_0`.`t_sys_config`;
CREATE TABLE `scm_0`.`t_sys_config` (
  `config_id` BIGINT(20) NOT NULL COMMENT '配置ID',
  `config_code` VARCHAR(30) NOT NULL COMMENT '配置编码',
  `config_value` VARCHAR(100) NOT NULL COMMENT '配置值',
  `config_desc` VARCHAR(500) DEFAULT NULL COMMENT '配置说明',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` BIGINT(20) NOT NULL DEFAULT 0 COMMENT '创建人ID',
	`update_time` DATETIME NOT NULL ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `update_user_id` BIGINT(20) NOT NULL DEFAULT 0 COMMENT '最后更新用户ID',
  PRIMARY KEY (`config_id`),
	UNIQUE KEY `uk_config_code`(`config_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '系统配置信息';

DROP TABLE IF EXISTS `scm_1`.`t_sys_config`;
CREATE TABLE `scm_1`.`t_sys_config` (
  `config_id` BIGINT(20) NOT NULL COMMENT '配置ID',
  `config_code` VARCHAR(30) NOT NULL COMMENT '配置编码',
  `config_value` VARCHAR(100) NOT NULL COMMENT '配置值',
  `config_desc` VARCHAR(500) DEFAULT NULL COMMENT '配置说明',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` BIGINT(20) NOT NULL DEFAULT 0 COMMENT '创建人ID',
	`update_time` DATETIME NOT NULL ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `update_user_id` BIGINT(20) NOT NULL DEFAULT 0 COMMENT '最后更新用户ID',
  PRIMARY KEY (`config_id`),
	UNIQUE KEY `uk_config_code`(`config_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '系统配置信息';



/*
TRUNCATE TABLE `scm_0`.`t_tenant`;
TRUNCATE TABLE `scm_1`.`t_tenant`;
*/

-- 初始化租户
REPLACE INTO `scm_0`.`t_tenant`(`tenant_id`, `tenant_name`, `status`) VALUES(101, '美怡乐', 1);
REPLACE INTO `scm_1`.`t_tenant`(`tenant_id`, `tenant_name`, `status`) VALUES(101, '美怡乐', 1);
REPLACE INTO `scm_0`.`t_tenant`(`tenant_id`, `tenant_name`, `status`) VALUES(102, '喜乐滋', 1);
REPLACE INTO `scm_1`.`t_tenant`(`tenant_id`, `tenant_name`, `status`) VALUES(102, '喜乐滋', 1);

-- 创建操作日志表
DROP TABLE IF EXISTS `scm`.`t_operate_log`;
CREATE TABLE `scm`.`t_operate_log` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `operate_time` DATETIME NOT NULL COMMENT '操作时间',
  `log_content` VARCHAR(2000) DEFAULT NULL COMMENT '日志内容',
  `operator_id` bigint(20) NOT NULL COMMENT '操作用户ID',
	`create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` BIGINT(20) NOT NULL DEFAULT 0 COMMENT '创建人ID',
	`update_time` DATETIME NOT NULL ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `update_user_id` BIGINT(20) NOT NULL DEFAULT 0 COMMENT '最后更新用户ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 通过存储过程创建操作日志的分表
DROP PROCEDURE IF EXISTS proc_create_operate_log;
DELIMITER //
CREATE PROCEDURE proc_create_operate_log()
BEGIN
	DECLARE v_year INT DEFAULT 2019;
	DECLARE v_month INT DEFAULT 1;
	DECLARE v_schema_name VARCHAR(100);
	DECLARE v_table_name VARCHAR(200);

	WHILE v_year <= 2020
	DO
		IF v_year % 2 = 1 THEN
			SET v_schema_name = '`scm_1`';
		ELSE
			SET v_schema_name = '`scm_0`';
		END IF;

		SET v_month = 1;

		WHILE v_month <= 12
		DO
			IF v_month < 10 THEN
				SET v_table_name = CONCAT(v_schema_name, '.`t_operate_log_', v_year, '_0', v_month, '`');
			ELSE
				SET v_table_name = CONCAT(v_schema_name, '.`t_operate_log_', v_year, '_', v_month, '`');
			END IF;

			-- 先drop表
			SET @drop_table_sql = CONCAT('DROP TABLE IF EXISTS ', v_table_name);
			PREPARE temp FROM @drop_table_sql;
			EXECUTE temp;

			-- 创建表
			SET @create_table_sql = CONCAT("
						CREATE TABLE ", v_table_name, " (
							`id` bigint(20) NOT NULL COMMENT 'ID',
							`operate_time` TIMESTAMP NOT NULL COMMENT '操作时间',
							`log_content` VARCHAR(2000) DEFAULT NULL COMMENT '日志内容',
							`operator_id` bigint(20) NOT NULL COMMENT '操作用户ID',
							`create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
							`create_user_id` BIGINT(20) NOT NULL DEFAULT 0 COMMENT '创建人ID',
							`update_time` DATETIME NOT NULL ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
							`update_user_id` BIGINT(20) NOT NULL DEFAULT 0 COMMENT '最后更新用户ID',
							PRIMARY KEY (`id`)
						) ENGINE=InnoDB DEFAULT CHARSET=utf8;");
			PREPARE temp FROM @create_table_sql;
			EXECUTE temp;

			SET v_month = v_month + 1;
		END WHILE;

		SET v_year = v_year + 1;
	END WHILE;
END //
DELIMITER ;

CALL proc_create_operate_log();

/*
通过存储过程创建采购单和采购单货品明细表。采购单按租户ID分库，按租户ID_年份 分表
CREATE TABLE `scm_x`.`t_purchase_order` (
	`order_id` BIGINT(20) NOT NULL COMMENT '采购单ID',
	`tenant_id` BIGINT(20) NOT NULL COMMENT '租户ID',
	`order_date` DATE NOT NULL COMMENT '采购日期',
	`supplier_name` VARCHAR(100) NOT NULL COMMENT '供货商名称',
	`amount` DECIMAL(10, 2) NOT NULL DEFAULT 0 COMMENT '采购单总金额',
	`purchaser_id` BIGINT(20) DEFAULT NULL COMMENT '采购员ID',
	`create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` BIGINT(20) NOT NULL DEFAULT 0 COMMENT '创建人ID',
	`update_time` DATETIME NOT NULL ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `update_user_id` BIGINT(20) NOT NULL DEFAULT 0 COMMENT '最后更新用户ID',
	PRIMARY KEY (`order_id`),
	INDEX `idx_tenant_id`(`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '采购单';

CREATE TABLE `scm_x`.`t_purchase_item` (
	`item_id` BIGINT(20) NOT NULL COMMENT '采购货品明细ID',
	`order_id` BIGINT(20) NOT NULL COMMENT '采购单ID',
	`tenant_id` BIGINT(20) NOT NULL COMMENT '租户ID',
	`order_date` DATE NOT NULL COMMENT '采购日期',
	`product_id` BIGINT(20) NOT NULL COMMENT '货品ID',
	`quantity` DECIMAL(10, 2) NOT NULL COMMENT '采购数量',
	`price` DECIMAL(10, 2) NOT NULL COMMENT '采购单价',
	`create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` BIGINT(20) NOT NULL DEFAULT 0 COMMENT '创建人ID',
	`update_time` DATETIME NOT NULL ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `update_user_id` BIGINT(20) NOT NULL DEFAULT 0 COMMENT '最后更新用户ID',
	PRIMARY KEY (`item_id`),
	UNIQUE KEY `uk_purchase_item`(`order_id`, `product_id`),
	INDEX `idx_tenant_id`(`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '采购货品明细';


*/
DROP PROCEDURE IF EXISTS proc_create_purchase_order;
DELIMITER //
CREATE PROCEDURE proc_create_purchase_order()
BEGIN
	DECLARE v_tenant_id INT DEFAULT 1;
	DECLARE v_year INT DEFAULT 2018;
	DECLARE v_schema_name VARCHAR(100);
	DECLARE v_table_name VARCHAR(200);

	WHILE v_tenant_id <= 2
	DO
		IF v_tenant_id % 2 = 1 THEN
			SET v_schema_name = '`scm_1`';
		ELSE
			SET v_schema_name = '`scm_0`';
		END IF;

		SET v_year = 2018;
		WHILE v_year <= 2021
		DO
			SET v_table_name = CONCAT(v_schema_name, '.`t_purchase_order_10', v_tenant_id, '_', v_year, '`');

			-- 先drop表
			SET @drop_table_sql = CONCAT('DROP TABLE IF EXISTS ', v_table_name);
			PREPARE temp FROM @drop_table_sql;
      EXECUTE temp;

			-- 创建表
			SET @create_table_sql = CONCAT("
						CREATE TABLE ", v_table_name, " (
							`order_id` BIGINT(20) NOT NULL COMMENT '采购单ID',
							`tenant_id` BIGINT(20) NOT NULL COMMENT '租户ID',
							`order_date` DATE NOT NULL COMMENT '采购日期',
							`supplier_name` VARCHAR(100) NOT NULL COMMENT '供货商名称',
							`amount` DECIMAL(10, 2) NOT NULL DEFAULT 0 COMMENT '采购单总金额',
							`purchaser_id` BIGINT(20) DEFAULT NULL COMMENT '采购员ID',
							`create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
							`create_user_id` BIGINT(20) NOT NULL DEFAULT 0 COMMENT '创建人ID',
							`update_time` DATETIME NOT NULL ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
							`update_user_id` BIGINT(20) NOT NULL DEFAULT 0 COMMENT '最后更新用户ID',
							PRIMARY KEY (`order_id`),
							INDEX `idx_tenant_id`(`tenant_id`)
						) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '采购单';");
			PREPARE temp FROM @create_table_sql;
      EXECUTE temp;

			SET v_table_name = CONCAT(v_schema_name, '.`t_purchase_item_10', v_tenant_id, '_', v_year, '`');

			-- 先drop表
			SET @drop_table_sql = CONCAT('DROP TABLE IF EXISTS ', v_table_name);
			PREPARE temp FROM @drop_table_sql;
      EXECUTE temp;

			-- 创建表
			SET @create_table_sql = CONCAT("
						CREATE TABLE ", v_table_name, " (
							`item_id` BIGINT(20) NOT NULL COMMENT '采购货品明细ID',
							`order_id` BIGINT(20) NOT NULL COMMENT '采购单ID',
							`tenant_id` BIGINT(20) NOT NULL COMMENT '租户ID',
							`order_date` DATE NOT NULL COMMENT '采购日期',
							`product_id` BIGINT(20) NOT NULL COMMENT '货品ID',
							`quantity` DECIMAL(10, 2) NOT NULL COMMENT '采购数量',
							`price` DECIMAL(10, 2) NOT NULL COMMENT '采购单价',
							`create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
							`create_user_id` BIGINT(20) NOT NULL DEFAULT 0 COMMENT '创建人ID',
							`update_time` DATETIME NOT NULL ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
							`update_user_id` BIGINT(20) NOT NULL DEFAULT 0 COMMENT '最后更新用户ID',
							PRIMARY KEY (`item_id`),
							UNIQUE KEY `uk_purchase_item`(`order_id`, `product_id`),
							INDEX `idx_tenant_id`(`tenant_id`)
						) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '采购货品明细';");
			PREPARE temp FROM @create_table_sql;
      EXECUTE temp;

			SET v_year = v_year + 1;
		END WHILE;

		SET v_tenant_id = v_tenant_id + 1;
	END WHILE;
END //
DELIMITER ;

CALL proc_create_purchase_order();



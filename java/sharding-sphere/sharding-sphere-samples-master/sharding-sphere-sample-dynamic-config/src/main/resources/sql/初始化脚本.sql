/******************************************
下面的脚本是为演示如下分库分表场景：
订单主表order_main按订单ID分库分表，订单明细 order_detail 根据订单ID分库分表。

分库分表的结构如下：
    数据库 dynamic_config_0 包含表：
        order_main_0、order_main_2、
        order_detail_0、order_detail_2
    数据库 dynamic_config_1 包含表：
        order_main_1、order_main_3、
        order_detail_1、order_detail_3

分库的算法：订单ID % 2
分表的算法：订单ID % 4

******************************************/
-- 建库脚本
CREATE DATABASE IF NOT EXISTS `dynamic_config_0` DEFAULT CHARACTER SET utf8;
CREATE DATABASE IF NOT EXISTS `dynamic_config_1` DEFAULT CHARACTER SET utf8;

-- 通过存储过程创建操作日志的分表
DROP PROCEDURE IF EXISTS proc_create_order;
DELIMITER //
CREATE PROCEDURE proc_create_order()
BEGIN
	DECLARE v_seq INT DEFAULT 0;
	DECLARE v_mod INT DEFAULT 0;
	DECLARE v_table_name VARCHAR(500);

	WHILE v_seq <= 3
	DO
	    SET v_mod = v_seq % 2;

		-- 订单主表
		SET v_table_name = CONCAT('dynamic_config_', v_mod, '.order_main_', v_seq);
		-- 先删除订单主表
        SET @drop_table_sql = CONCAT("DROP TABLE IF EXISTS ", v_table_name);
        PREPARE temp FROM @drop_table_sql;
        EXECUTE temp;
		-- 创建订单主表
        SET @create_table_sql = CONCAT("
                CREATE TABLE ", v_table_name, " (
                    `order_id` bigint(20) NOT NULL,
                    `order_date` date NOT NULL,
                    `amount` double DEFAULT NULL,
                    `creator_id` bigint(20) DEFAULT NULL,
                    PRIMARY KEY (`order_id`)
                ) ENGINE=InnoDB DEFAULT CHARSET=utf8;");
        PREPARE temp FROM @create_table_sql;
        EXECUTE temp;

        -- 订单明细表
        SET v_table_name = CONCAT('dynamic_config_', v_mod, '.order_detail_', v_seq);
        -- 先删除订单明细表
        SET @drop_table_sql = CONCAT("DROP TABLE IF EXISTS ", v_table_name);
        PREPARE temp FROM @drop_table_sql;
        EXECUTE temp;
        -- 创建订单明细表
        SET @create_table_sql = CONCAT("
                CREATE TABLE ", v_table_name, " (
                    `id` bigint(20) NOT NULL,
                    `order_id` bigint(20) NOT NULL,
                    `product_id` bigint(20) NOT NULL,
                    `quantity` int NOT NULL,
                    `price` decimal(10, 2) NOT NULL,
                    `creator_id` bigint(20) DEFAULT NULL,
                    PRIMARY KEY (`id`),
                    INDEX uk_product_id(`order_id`, `product_id`)
                ) ENGINE=InnoDB DEFAULT CHARSET=utf8;");
        PREPARE temp FROM @create_table_sql;
        EXECUTE temp;

		SET v_seq = v_seq + 1;
	END WHILE;
END //
DELIMITER ;

CALL proc_create_order();
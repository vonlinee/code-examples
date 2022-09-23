-- https://blog.csdn.net/weixin_43180484/article/details/109527556
CREATE TABLE t_sys_dict_type
(
    `DICT_ID`          bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典主键',
    `DICT_TYPE_CODE`   varchar(100) DEFAULT '' COMMENT '字典类型编码，约定命名规范',
    `DICT_TYPE_NAME`   varchar(100) DEFAULT '' COMMENT '字典类型名称，中文名称',
    `LEVEL`            tinyint      DEFAULT 0 COMMENT '层级',
    `PARENT_DICT_TYPE` bigint(20) NOT NULL COMMENT '父字典类型，关联DICT_ID字段',
    `IS_ENABLE`        char(1)      DEFAULT '0' COMMENT '状态(1正常/0停用)',
    `CREATE_BY`        varchar(64)  DEFAULT '' COMMENT '创建者',
    `CREATE_TIME`      datetime     DEFAULT NULL COMMENT '创建时间',
    `UPDATE_BY`        varchar(64)  DEFAULT '' COMMENT '更新者',
    `UPDATE_TIME`      datetime     DEFAULT NULL COMMENT '更新时间',
    `REMARK`           varchar(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (dict_id),
    UNIQUE KEY dict_type_code (`DICT_TYPE_CODE`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8 COMMENT = '字典类型表';

-- 通过DICT_TYPE_CODE关联
CREATE TABLE t_sys_dict_data
(
    `DICT_DATA_ID`   int(11) NOT NULL AUTO_INCREMENT COMMENT '字典数据主键',
    `DICT_TYPE_CODE` varchar(100) DEFAULT '' COMMENT '字典类型，关联t_sys_dict_type表',
    `DICT_LABEL`     varchar(100) DEFAULT '' COMMENT '字典标签',
    `DICT_VALUE`     varchar(100) DEFAULT '' COMMENT '字典键值',
    `SORT_NUM`       int(4)       DEFAULT 0 COMMENT '字典排序',
    `CSS_CLASS`      varchar(100) DEFAULT '' COMMENT '样式属性(其他样式扩展)',
    `LIST_CLASS`     varchar(100) DEFAULT '' COMMENT '表格回显样式',
    `IS_DEFAULT`     char(1)      DEFAULT 'N' COMMENT '是否默认选中(Y是 N否)',
    `IS_ENABLE`      char(1)      DEFAULT '0' COMMENT '状态(1正常/0停用)',
    `CREATE_BY`      varchar(64)  DEFAULT '' COMMENT '创建者',
    `CREATE_TIME`    datetime     DEFAULT NULL COMMENT '创建时间',
    `UPDATE_BY`      varchar(64)  DEFAULT '' COMMENT '更新者',
    `UPDATE_TIME`    datetime     DEFAULT NULL COMMENT '更新时间',
    `REMARK`         varchar(500) DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`DICT_DATA_ID`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8
  ROW_FORMAT = COMPACT COMMENT = '字典数据表';

-- TODO 添加嵌套层级

# 菜单表




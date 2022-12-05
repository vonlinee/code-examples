-- 删除数据库
drop database if exists `cloud_resource_management`;
-- 新建数据库
create database `cloud_resource_management` default character set utf8mb4;
-- 使用数据库
use `cloud_resource_management`;

/*
 片区、部门、用户、身份（角色）、权限
 片区：部门 = 1：N
 部门：用户 = 1：N
 部门相互之间没有层级关系
 Role-Based Access Control，RBAC，基于角色的权限控制
 */
-- 片区表
CREATE TABLE `area` (
    `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '自增主键',
    `area_id` VARCHAR(255) NOT NULL UNIQUE COMMENT '片区ID',
    `area_name` VARCHAR(255) NULL COMMENT '片区名称',
    `leader_no` VARCHAR(255) NULL COMMENT '片区领导，值为user表staff_no的值',
    `create_time` DATETIME NOT NULL COMMENT '创建时间',
    `update_time` DATETIME NOT NULL COMMENT '更新时间'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COMMENT '片区表';

-- 部门表
CREATE TABLE `dept` (
    `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '自增主键',
    `dept_no` VARCHAR(255) NOT NULL UNIQUE COMMENT '部门编号',
    `dept_name` VARCHAR(255) NULL COMMENT '部门名称',
    `area_id` INT UNSIGNED NULL COMMENT '所属片区ID，值为area表主键',
    `leader_no` VARCHAR(255) NULL COMMENT '片区领导，值为user表staff_no的值',
    `create_time` DATETIME NOT NULL COMMENT '创建时间',
    `update_time` DATETIME NOT NULL COMMENT '更新时间'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COMMENT '部门表';

-- 用户表
CREATE TABLE `user` (
    `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '自增主键',
    `dept_id` INT UNSIGNED NULL COMMENT '所属部门主键，外键',
    `dept_no` VARCHAR(255) NULL COMMENT '所属部门编号，冗余字段',
    `staff_no` VARCHAR(255) NOT NULL UNIQUE COMMENT '员工工号，也是登录名',
    `staff_name` VARCHAR(255) NULL COMMENT '员工姓名',
    `user_type` TINYINT NOT NULL DEFAULT 0 COMMENT '用户类型，0管理员、1普通员工、2资源负责人、3产品总监、4副院长',
    `password` VARCHAR(255) NULL COMMENT '登录密码',
    `phone_number` VARCHAR(20) NULL COMMENT '手机号',
    `email` VARCHAR(255) NULL COMMENT '电子邮箱',
    `avatar` VARCHAR(255) NULL COMMENT '用户头像',
    `post_id` VARCHAR(255) NULL COMMENT '岗位ID',
    `post_name` VARCHAR(255) NULL COMMENT '岗位名称',
    `login_ip` VARCHAR(255) NULL COMMENT '最后登录IP',
    `login_date` DATETIME NULL COMMENT '最后登录时间',
    `create_time` DATETIME NOT NULL COMMENT '创建时间',
    `update_time` DATETIME NOT NULL COMMENT '更新时间'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COMMENT '用户表';

-- 角色表
CREATE TABLE `role` (
    `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '自增主键',
    `role_name` VARCHAR(255) NOT NULL UNIQUE COMMENT '角色名称，唯一',
    `role_key` VARCHAR(255) NOT NULL COMMENT '角色权限字符串，Shiro RequiresRoles鉴权时使用',
    `creator` INT UNSIGNED NULL COMMENT '角色创建者ID，-1为系统预置角色',
    `create_time` DATETIME NOT NULL COMMENT '创建时间',
    `update_time` DATETIME NOT NULL COMMENT '更新时间'
) ENGINE = InnoDB AUTO_INCREMENT = 1000 CHARACTER SET = utf8mb4 COMMENT '角色表';

-- TODO 预定义角色

-- 权限表
CREATE TABLE `authority` (
    `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '自增主键',
    `name` VARCHAR(255) NOT NULL UNIQUE COMMENT '权限名称',
    `permission` VARCHAR(255) NOT NULL COMMENT '权限标识，Shiro RequiresPermissions鉴权时使用',
    `creator` INT UNSIGNED NULL COMMENT '权限创建者ID，-1为系统预置权限',
    `create_time` DATETIME NOT NULL COMMENT '创建时间',
    `update_time` DATETIME NOT NULL COMMENT '更新时间'
) ENGINE = InnoDB AUTO_INCREMENT = 1000 CHARACTER SET = utf8mb4 COMMENT '权限表';

-- 预定义权限

-- 用户、角色关联表 用户N-N角色
CREATE TABLE `user_role` (
    `user_id` INT UNSIGNED NOT NULL COMMENT '用户ID',
    `role_id` INT UNSIGNED NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`user_id`, `role_id`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COMMENT '用户角色关联表';

-- TODO 初始化用户和角色关联表数据

-- 角色、权限关联表 角色N-N权限
CREATE TABLE `role_authority` (
    `role_id` INT UNSIGNED  NOT NULL COMMENT '角色ID',
    `authority_id` INT UNSIGNED  NOT NULL COMMENT '权限ID',
    PRIMARY KEY (`role_id`, `authority_id`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COMMENT '角色权限关联表';

-- 初始化角色和菜单关联表数据

/*
 项目、产品、云资源
 一个项目下有多个产品，一个产品可以属于多个项目，一个云资源可属于多个项目或多个产品
 在项目、产品、资源关联表中，注意可能出现的：1、只有项目；2、只有项目和产品；3、只有项目和资源；4项目、产品、资源都有
 */

-- 产品表
CREATE TABLE `product` (
    `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '自增主键',
    `product_name` VARCHAR(255) NOT NULL COMMENT '产品名称',
    `product_no` VARCHAR(255) NULL COMMENT '产品编号，预留字段',
    `version` VARCHAR(255) NULL COMMENT '产品版本号，预留字段',
    `creator` INT UNSIGNED NULL COMMENT '记录创建者ID',
    `create_time` DATETIME NOT NULL COMMENT '创建时间',
    `update_time` DATETIME NOT NULL COMMENT '更新时间',
    INDEX `idx_product_name` (`product_name`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COMMENT '产品表';

-- 项目表
CREATE TABLE `project` (
    `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '自增主键',
    `project_name` VARCHAR(255) NOT NULL COMMENT '项目名称',
    `director` VARCHAR(255) NULL COMMENT '项目负责人',
    `director_phone` VARCHAR(20) NULL COMMENT '负责人联系电话',
    `description` VARCHAR(500) NULL COMMENT '项目简介',
    `create_time` DATETIME NOT NULL COMMENT '创建时间',
    `update_time` DATETIME NOT NULL COMMENT '更新时间',
    INDEX `idx_product_name` (`project_name`),
    INDEX `idx_director` (`director`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COMMENT '项目表';

/*
-- 产品、项目、关联表
CREATE TABLE `project_product` (
    `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '自增主键',
    `project_id` INT UNSIGNED NOT NULL COMMENT '项目记录主键，外键',
    `product_id` INT UNSIGNED NOT NULL COMMENT '产品记录主键，外键',
    `create_time` DATETIME NOT NULL COMMENT '创建时间',
    `update_time` DATETIME NOT NULL COMMENT '更新时间'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COMMENT '项目、产品关联表';
*/

-- 产品、项目、资源关联表
CREATE TABLE `project_product_resource` (
    `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '自增主键',
    `project_id` INT UNSIGNED NOT NULL COMMENT '项目ID',
    `product_id` INT UNSIGNED NULL COMMENT '产品ID',
    `resource_id` INT UNSIGNED NULL COMMENT '资源基础表resource_base主键',
    `storage_time` DATETIME NOT NULL COMMENT '入库时间',
    `process_mode` VARCHAR(255) NULL COMMENT '到期处理方式：到期续费、到期不续费、到期转按需',
    INDEX `idx_project` (`project_id`),
    INDEX `idx_product` (`product_id`),
    INDEX `idx_storage` (`storage_time`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COMMENT '产品-项目-资源关联关系';


-- 资源授权表（资源：授权 = 1：N）
CREATE TABLE `resource_auth` (
    `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '自增主键',
    `relation_id` INT UNSIGNED NOT NULL COMMENT '产品、项目、资源关联表主键',
    `user_id` INT UNSIGNED NOT NULL COMMENT '被授权人ID，user表主键，外键',
    `staff_name` VARCHAR(255) NULL COMMENT '员工姓名，冗余字段',
    `create_time` DATETIME NULL COMMENT '创建时间',
    `update_time` DATETIME NULL COMMENT '更新时间',
    INDEX `idx_relation` (`relation_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COMMENT '云资源授权表';


/*
 抽取出各类资源的共有属性放在resource_base表中，特有属性放置在各个具体资源表中，且resource_base表记录具体资源的表名和主键
 产品、项目、资源关联表与resource_base的主键进行关联
 */
-- 资源基础信息表
CREATE TABLE `resource_base` (
    `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '自增主键',
    `resource_name` VARCHAR(255) NOT NULL COMMENT '资源名称',
    `provider` VARCHAR(255) NULL COMMENT '厂商',
    `billing_method` VARCHAR(255) NULL COMMENT '计费方式',
    `specification` VARCHAR(255) NULL COMMENT '规格参数',
    `start_time` DATETIME NULL COMMENT '开通时间',
    `expire_time` DATETIME NULL COMMENT '到期时间',
    `table_name` VARCHAR(255) NOT NULL COMMENT '资源详情表名，资源类型',
    `table_id` INT UNSIGNED NOT NULL COMMENT '资源详细表主键',
    `create_time` DATETIME NULL COMMENT '创建时间',
    `update_time` DATETIME NULL COMMENT '更新时间',
    INDEX `idx_res_name` (`resource_name`),
    INDEX `idx_res_type` (`table_name`)
    -- 厂商、计费方式区分度不大，暂不创建索引
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COMMENT '云资源基础信息表';

/*
 各类云资源：弹性云服务器、云硬盘、云数据库、弹性公网、弹性负载均衡、分布式缓存服务、分布式消息服务、
 对象存储服务、短信服务、CDN服务、视频直播、实时音视频实时音视频、即时通信IM、消息队列MQ、SSL证书服务、域名服务
 */

-- 弹性云服务器
CREATE TABLE `resource_ecs` (
    `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '自增主键',
    `resource_id` VARCHAR(255) NOT NULL COMMENT '资源唯一ID',
    `provider` VARCHAR(255) NULL COMMENT '厂商',
    `state` VARCHAR(255) NULL COMMENT '状态',
    `region` VARCHAR(255) NULL COMMENT '区域',
    `cpu` VARCHAR(255) NULL COMMENT 'CPU',
    `memory` VARCHAR(255) NULL COMMENT '内存，单位GB',
    `os` VARCHAR(255) NULL COMMENT '操作系统',
    `login_name` VARCHAR(255) NULL COMMENT '登录用户名',
    `password` VARCHAR(255) NULL COMMENT '登录密码',
    `private_ip` VARCHAR(255) NULL COMMENT '私有/内网IP',
    `public_ip` VARCHAR(255) NULL COMMENT '弹性公网IP',
    `bandwidth` VARCHAR(255) NULL COMMENT '带宽',
    `bandwidth_type` VARCHAR(255) NULL COMMENT '带宽类型'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COMMENT '弹性云服务器elastic-cloud-server';

-- 云硬盘
CREATE TABLE `resource_disk` (
    `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '自增主键',
    `resource_id` VARCHAR(255) NOT NULL COMMENT '资源唯一ID',
    `provider` VARCHAR(255) NULL COMMENT '厂商',
    `state` VARCHAR(255) NULL COMMENT '状态',
    `region` VARCHAR(255) NULL COMMENT '区域',
    `attribute` VARCHAR(255) NULL COMMENT '磁盘属性',
    `ecs_id` INT UNSIGNED NULL COMMENT '弹性云服务器主键，预留字段',
    `mount_ecs_name` VARCHAR(255) NULL COMMENT '挂载云服务器名称',
    `mount_ecs_id` VARCHAR(255) NULL COMMENT '挂载云服务器ID',
    `mound_point` VARCHAR(255) NULL COMMENT '挂载点',
    `disk_mode` VARCHAR(255) NULL COMMENT '磁盘模式',
    `encryption_disk` VARCHAR(255) NULL COMMENT '加密盘'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COMMENT '云硬盘';

-- 云数据库
CREATE TABLE `resource_database` (
     `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '自增主键',
     `resource_id` VARCHAR(255) NOT NULL COMMENT '资源唯一ID，云服务器ID',
     `provider` VARCHAR(255) NULL COMMENT '厂商',
     `state` VARCHAR(255) NULL COMMENT '状态',
     `region` VARCHAR(255) NULL COMMENT '区域',
     `instance_type` VARCHAR(255) NULL COMMENT '实例类型',
     `engine` VARCHAR(255) NULL COMMENT '数据库引擎',
     `private_ip` VARCHAR(255) NULL COMMENT '内网地址',
     `port` VARCHAR(255) NULL COMMENT '数据库端口',
     `public_ip` VARCHAR(255) NULL COMMENT '弹性公网IP',
     `storage_type` VARCHAR(255) NULL COMMENT '存储空间类型',
     `storage_size` INT UNSIGNED NULL COMMENT '存储空间大小GB',
     `vpc` VARCHAR(255) NULL COMMENT 'VPC',
     `vpc_id` VARCHAR(255) NULL COMMENT 'VPC ID',
     `sync_type` VARCHAR(255) NULL COMMENT '数据同步方式',
     `switch_strategy` VARCHAR(255) NULL COMMENT '切换策略'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COMMENT '云数据库';

-- 弹性公网
CREATE TABLE `resource_eip` (
    `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '自增主键',
    `resource_id` VARCHAR(255) NOT NULL COMMENT '资源唯一ID，公网IP ID',
    `provider` VARCHAR(255) NULL COMMENT '厂商',
    `state` VARCHAR(255) NULL COMMENT '状态',
    `eip` VARCHAR(255) NULL COMMENT '弹性公网IP',
    `instance_type` VARCHAR(255) NULL COMMENT '实例类型',
    `instance_name` VARCHAR(255) NULL COMMENT '绑定的云服务器名称',
    `instance_id` VARCHAR(255) NULL COMMENT '绑定的云服务器ID',
    `ecs_id` INT UNSIGNED NULL COMMENT '绑定的云服务器，外键'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COMMENT '弹性公网elastic-ip';

-- 弹性负载均衡
CREATE TABLE `resource_elb` (
    `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '自增主键',
    `resource_id` VARCHAR(255) NOT NULL COMMENT '资源唯一ID',
    `provider` VARCHAR(255) NULL COMMENT '厂商',
    `state` VARCHAR(255) NULL COMMENT '状态',
    `eip_id` INT UNSIGNED NULL COMMENT '弹性公网IP主键，外键',
    `eip` VARCHAR(255) NULL COMMENT '弹性公网IP',
    `private_id` VARCHAR(255) NULL COMMENT '私有/内网IP',
    `bandwidth` VARCHAR(255) NULL COMMENT '带宽',
    `eip_billing_mode` VARCHAR(255) NULL COMMENT 'EIP计费模式',
    `bandwidth_billing_mode` VARCHAR(255) NULL COMMENT '带宽计费模式'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COMMENT '弹性负载均衡elastic-load-balance';

-- 分布式缓存服务
CREATE TABLE `resource_cache` (
    `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '自增主键',
    `resource_id` VARCHAR(255) NOT NULL COMMENT '资源唯一ID',
    `provider` VARCHAR(255) NULL COMMENT '厂商',
    `state` VARCHAR(255) NULL COMMENT '状态',
    `region` VARCHAR(255) NULL COMMENT '可用区',
    `engine` VARCHAR(255) NULL COMMENT '缓存引擎',
    `instance_type` VARCHAR(255) NULL COMMENT '实例类型',
    `is_available` VARCHAR(255) NULL COMMENT '已用/可用',
    `connect_addr` VARCHAR(255) NULL COMMENT '连接地址',
    `vpc` VARCHAR(255) NULL COMMENT 'VPC',
    `vpc_id` VARCHAR(255) NULL COMMENT 'VPC ID',
    `domain` VARCHAR(255) NULL COMMENT '域名地址'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COMMENT '分布式缓存服务';

-- 分布式消息服务
CREATE TABLE `resource_dms` (
    `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '自增主键',
    `resource_id` VARCHAR(255) NOT NULL COMMENT '资源唯一ID',
    `provider` VARCHAR(255) NULL COMMENT '厂商',
    `state` VARCHAR(255) NULL COMMENT '状态',
    `instance_type` VARCHAR(255) NULL COMMENT '实例类型',
    `version` VARCHAR(255) NULL COMMENT '版本',
    `is_available` VARCHAR(255) NULL COMMENT '已用/可用',
    `disk_type` VARCHAR(255) NULL COMMENT '磁盘类型',
    `engine` VARCHAR(255) NULL COMMENT '消息引擎',
    `description` VARCHAR(255) NULL COMMENT '描述',
    `vpc` VARCHAR(255) NULL COMMENT 'VPC',
    `connect_addr` VARCHAR(255) NULL COMMENT '连接地址',
    `username` VARCHAR(255) NULL COMMENT '用户名',
    `ssl` VARCHAR(255) NULL COMMENT 'ssl',
    `public_access` VARCHAR(255) NULL COMMENT '公网访问',
    `eip` VARCHAR(255) NULL COMMENT '弹性公网IP',
    `eip_id` INT UNSIGNED NULL COMMENT '弹性公网IP主键，外键'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COMMENT '分布式消息服务distributed-message-service';

-- 对象存储服务
CREATE TABLE `resource_obs` (
    `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '自增主键',
    `resource_id` VARCHAR(255) NOT NULL COMMENT '资源唯一ID，同名称',
    `provider` VARCHAR(255) NULL COMMENT '厂商',
    `storage_type` VARCHAR(255) NULL COMMENT '存储类别',
    `region` VARCHAR(255) NULL COMMENT '区域',
    `storage_strategy` VARCHAR(255) NULL COMMENT '数据冗余存储策略',
    `storage_size` VARCHAR(255) NULL COMMENT '存储容量',
    `bucket_strategy` VARCHAR(255) NULL COMMENT '桶策略',
    `bucket_version` VARCHAR(255) NULL COMMENT '桶版本号',
    `access_key` VARCHAR(255) NULL COMMENT 'AK',
    `secret_key` VARCHAR(255) NULL COMMENT 'SK'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COMMENT '对象存储服务';

-- 短信服务
CREATE TABLE `resource_sms` (
    `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '自增主键',
    `resource_id` VARCHAR(255) NOT NULL COMMENT '资源ID，短信模板ID',
    `provider` VARCHAR(255) NULL COMMENT '厂商',
    `template_type` VARCHAR(255) NULL COMMENT '模板类型',
    `template_sign` VARCHAR(255) NULL COMMENT '模板签名',
    `access_key` VARCHAR(255) NULL COMMENT 'AK',
    `secret_key` VARCHAR(255) NULL COMMENT 'SK'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COMMENT '短信服务';

-- 内容分发网络CDN服务
CREATE TABLE `resource_cdn` (
    `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '自增主键',
    `resource_id` VARCHAR(255) NOT NULL COMMENT '资源ID，域名',
    `provider` VARCHAR(255) NULL COMMENT '厂商',
    `cname` VARCHAR(255) NULL COMMENT 'CNAME，域名别名',
    `domain_state` VARCHAR(255) NULL COMMENT '域名状态',
    `business_type` VARCHAR(255) NULL COMMENT '业务类型',
    `https_status` VARCHAR(255) NULL COMMENT 'HTTPS状态',
    `main_source_type` VARCHAR(255) NULL COMMENT '主源站类型',
    `main_source_addr` VARCHAR(255) NULL COMMENT '主源站地址',
    `main_source_host` VARCHAR(255) NULL COMMENT '主源站回源host',
    `main_source_https_port` VARCHAR(255) NULL COMMENT '主源站HTTPS端口',
    `main_source_http_port` VARCHAR(255) NULL COMMENT '主源站HTTP端口',
    `source_host` VARCHAR(255) NULL COMMENT '回源host'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COMMENT 'CDN服务';

-- 视频直播
CREATE TABLE `resource_video_live` (
    `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '自增主键',
    `resource_id` VARCHAR(255) NOT NULL COMMENT '资源ID，域名',
    `provider` VARCHAR(255) NULL COMMENT '厂商',
    `cname` VARCHAR(255) NULL COMMENT 'CNAME，域名别名',
    `cname_state` VARCHAR(255) NULL COMMENT 'CNAME状态',
    `business_type` VARCHAR(255) NULL COMMENT '业务类型',
    `live_center` VARCHAR(255) NULL COMMENT '直播中心',
    `running_state` VARCHAR(255) NULL COMMENT '运行状态'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COMMENT '视频直播';

-- 实时音视频
CREATE TABLE `resource_rtc` (
    `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '自增主键',
    `resource_id` VARCHAR(255) NOT NULL COMMENT '资源ID，SDK-APP-ID',
    `provider` VARCHAR(255) NULL COMMENT '厂商',
    `state` VARCHAR(255) NULL COMMENT '服务状态',
    `secret_key` VARCHAR(255) NULL COMMENT '密钥'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COMMENT '实时音视频realtime-communication';

-- 即时通信
CREATE TABLE `resource_im` (
    `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '自增主键',
    `resource_id` VARCHAR(255) NOT NULL COMMENT '资源ID，SDK-APP-ID',
    `provider` VARCHAR(255) NULL COMMENT '厂商',
    `state` VARCHAR(255) NULL COMMENT '服务状态',
    `secret_key` VARCHAR(255) NULL COMMENT '密钥',
    `business_package` VARCHAR(255) NULL COMMENT '套餐包，package是java关键字，所以前面多加business'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COMMENT '即时通信instant-messaging';

-- 消息队列
CREATE TABLE `resource_mq` (
    `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '自增主键',
    `resource_id` VARCHAR(255) NOT NULL COMMENT '资源ID',
    `provider` VARCHAR(255) NULL COMMENT '厂商',
    `version` VARCHAR(255) NULL COMMENT '版本',
    `instance_type` VARCHAR(255) NULL COMMENT '实例类型',
    `running_state` VARCHAR(255) NULL COMMENT '运行状态',
    `topic` VARCHAR(255) NULL COMMENT 'topic',
    `group` VARCHAR(255) NULL COMMENT 'group'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COMMENT '消息队列message-queue';

-- SSL证书服务
CREATE TABLE `resource_ssl` (
    `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '自增主键',
    `resource_id` VARCHAR(255) NOT NULL COMMENT '资源ID，证书ID',
    `provider` VARCHAR(255) NULL COMMENT '厂商',
    `certificate_type` VARCHAR(255) NULL COMMENT '证书类型',
    `binding_domain` VARCHAR(255) NULL COMMENT '绑定域名'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COMMENT 'SSL证书服务-security-socket-layer';

-- 域名服务
CREATE TABLE `resource_dns` (
    `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '自增主键',
    `resource_id` VARCHAR(255) NOT NULL COMMENT '资源ID，域名',
    `provider` VARCHAR(255) NULL COMMENT '厂商',
    `user_type` VARCHAR(255) NULL COMMENT '用户类型',
    `domain_owner` VARCHAR(255) NULL COMMENT '域名所有者',
    `domain_state` VARCHAR(255) NULL COMMENT '域名状态',
    `server` VARCHAR(255) NULL COMMENT '域名服务器'
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COMMENT '域名服务-domain-name-server';

/*
 密钥申请、密钥申请审核 = 1：N
 可申请密钥的四种资源：短信服务、对象存储服务、实时音视频、即时通信
 */

-- 密钥申请
CREATE TABLE `key_application` (
    `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '自增主键',
    `relation_id` INT UNSIGNED NOT NULL COMMENT '产品、项目、资源关联表主键',
    `public_key` VARCHAR(255) NULL COMMENT '公钥，冗余字段',
    `proposer_id` INT UNSIGNED COMMENT '申请人ID，用户表主键，外键',
    `description` VARCHAR(255) NULL COMMENT '申请说明',
    `push_url` VARCHAR(255) NOT NULL COMMENT '密钥推送地址，一般为基础平台地址',
    `review_state` TINYINT NOT NULL DEFAULT 0 COMMENT '0待审核，1已通过，2已驳回',
    `create_time` DATETIME NULL COMMENT '创建时间，申请日期',
    `update_time` DATETIME NULL COMMENT '更新时间',
    INDEX `idx_public_key` (`public_key`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COMMENT '密钥申请记录';

-- 密钥申请审核
CREATE TABLE `application_review` (
    `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '自增主键',
    `application_id` INT UNSIGNED COMMENT '密钥申请主键，外键',
    `reviewer_id` INT UNSIGNED NOT NULL COMMENT '审核人ID，用户表主键，外键',
    `is_pass` TINYINT NOT NULL COMMENT '审核结果，0驳回，1通过',
    `review_comment` VARCHAR(255) NULL COMMENT '审核意见',
    `create_time` DATETIME NULL COMMENT '创建时间，申请日期',
    `update_time` DATETIME NULL COMMENT '更新时间'
)  ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COMMENT '密钥申请记录';
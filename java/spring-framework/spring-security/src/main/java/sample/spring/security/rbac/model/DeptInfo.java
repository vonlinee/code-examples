package sample.spring.security.rbac.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * create table `sys_dept`(
 * `id` int not null  auto_increment comment '部门id',
 * `name` VARCHAR(20) not null DEFAULT '' comment '部门名称',
 * `parent_id`  int  not null  DEFAULT 0  comment  '上级部门id' ,
 * `level`  VARCHAR(200) not null DEFAULT '' comment '部门层级',
 * `seq`  int  not null DEFAULT 0 comment '部门在当前层级下的顺序，由小到大',
 * `remark` VARCHAR(200) DEFAULT '' comment '备注' ,
 * `operator` VARCHAR(20) not null  DEFAULT '' COMMENT '操作者',
 * `operator_time` datetime  not null DEFAULT CURRENT_TIMESTAMP ON UPDATE
 * CURRENT_TIMESTAMP   COMMENT '最后一次操作时间' ,
 * `operator_ip` VARCHAR(20)  not null DEFAULT '' comment 	'最后一名操作者的ip' ,
 * PRIMARY KEY (`id`)
 * ) comment  '部门表';
 */
@Data
@Entity
@Table(name = "sys_dept")
public class DeptInfo {

    @Id
    @Column(name = "DEPT_ID", columnDefinition = "COMMENT '部门id'")
    private String deptId;

    private String deptName;
    private String parentId;
    private String level;

}

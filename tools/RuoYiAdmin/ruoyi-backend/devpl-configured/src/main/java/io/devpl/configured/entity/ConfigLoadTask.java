package io.devpl.configured.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 配置加载任务项实体类
 */
@Data
@Entity
@Table(name = "t_config_load_task")
public class ConfigLoadTask implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TASK_ID", columnDefinition = "VARCHAR(36) COMMENT '任务主键ID'")
    private String taskId;

    @Column(name = "TASK_CODE", columnDefinition = "VARCHAR(32) COMMENT '任务编码'")
    private String taskCode;

    @Column(name = "TASK_NAME", columnDefinition = "VARCHAR(32) COMMENT '任务名称'")
    private String taskName;

    @Column(name = "QUERY_SQL", columnDefinition = "VARCHAR(256) COMMENT '配置数据SQL'")
    private String querySql;

    @Column(name = "ORDER_NUM", columnDefinition = "int(2) COMMENT '排序号'")
    private Integer orderNum;

    @Column(name = "IS_ENABLE", columnDefinition = "tinyint(1) DEFAULT 1 COMMENT '逻辑删除字段'")
    private boolean isEnable;

    @Column(name = "CREATE_TIME", columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'")
    private LocalDateTime createTime;

    @Column(name = "UPDATE_TIME", columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP COMMENT '上次更新时间'")
    private LocalDateTime updateTime;

    @Column(name = "COMMENT", columnDefinition = "VARCHAR(100) COMMENT '任务描述'")
    private String comment;
}

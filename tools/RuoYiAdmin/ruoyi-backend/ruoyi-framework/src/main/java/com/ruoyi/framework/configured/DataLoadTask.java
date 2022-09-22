package com.ruoyi.framework.configured;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 数据加载任务
 */
@Data
@Entity
@Table(name = "t_data_load_task")
public class DataLoadTask implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TASK_ID")
    private int taskId;

    @Column(name = "SQL")
    private String sql;

    @Column(name = "ORDER_NUM")
    private int orderNum;
}

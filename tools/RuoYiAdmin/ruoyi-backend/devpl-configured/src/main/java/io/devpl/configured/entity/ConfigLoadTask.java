package io.devpl.configured.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 配置加载任务项实体类
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "t_config_load_task")
public class ConfigLoadTask {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TASK_ID", nullable = false)
    private Integer taskId;

    @Column(name = "TASK_NAME", length = 200)
    private String taskName;

    @Column(name = "QUERY_SQL", length = 200)
    private String querySql;

    @Column(name = "ORDER_NUM")
    private Integer orderNum;

    @Column(name = "IS_ENABLE")
    private boolean isEnable;

    @Column(name = "CREATE_TIME", columnDefinition = "创建时间")
    private LocalDateTime createTime;

    @Column(name = "UPDATE_TIME", columnDefinition = "上次更新时间")
    private LocalDateTime updateTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ConfigLoadTask that = (ConfigLoadTask) o;
        return taskId != null && Objects.equals(taskId, that.taskId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

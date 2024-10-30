package org.lancoo.crm.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "cron_task")
public class CronTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "task_name", nullable = false)
    private String taskName;

    @Column(name = "cron", nullable = false)
    private String cron;

    @Column(name = "description")
    private String description;
}

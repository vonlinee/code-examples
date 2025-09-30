package org.lancoo.crm.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "project_info")
public class ProjectInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "project_name", nullable = false)
    private String projectName;
}

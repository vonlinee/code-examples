package org.lancoo.crm.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "resource_info")
public class ResourceInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "resource_id")
    private String resourceId;

    @Column(name = "resource_name", nullable = false)
    private String resourceName;

    @Column(name = "resource_type", nullable = false)
    private String resourceType;
}
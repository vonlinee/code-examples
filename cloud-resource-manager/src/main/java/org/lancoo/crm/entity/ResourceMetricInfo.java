package org.lancoo.crm.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "resource_metric_info")
public class ResourceMetricInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "resource_id", nullable = false)
    private Long resourceId;

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ServerStatus status; // 使用枚举类型

    @Column(name = "region", nullable = false)
    private String region;

    @Column(name = "cpu_usage")
    private Double cpuUsage;

    @Column(name = "disk_usage")
    private Double diskUsage;

    @Column(name = "memory_usage")
    private Double memoryUsage;

    @Column(name = "inbound_traffic")
    private Double inboundTraffic;

    @Column(name = "outbound_traffic")
    private Double outboundTraffic;

    @Column(name = "io_utilization")
    private Double ioUtilization;

    @Column(name = "temperature")
    private Double temperature;

    @Column(name = "uptime")
    private String uptime;

    @Column(name = "instance_type")
    private String instanceType;

    @Column(name = "create_timestamp")
    private Long createTimestamp;

    @Column(name = "create_time")
    private String createTime;
}

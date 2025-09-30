package org.lancoo.crm.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Server {
    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("status")
    private String status;

    @JsonProperty("region")
    private String region;

    @JsonProperty("cpuUsage")
    private double cpuUsage;

    @JsonProperty("diskUsage")
    private double diskUsage;

    @JsonProperty("memoryUsage")
    private double memoryUsage;

    @JsonProperty("inboundTraffic")
    private double inboundTraffic;

    @JsonProperty("outboundTraffic")
    private double outboundTraffic;

    @JsonProperty("ioUtilization")
    private double ioUtilization;

    @JsonProperty("temperature")
    private double temperature;

    @JsonProperty("uptime")
    private String uptime;

    @JsonProperty("instanceType")
    private String instanceType;
}
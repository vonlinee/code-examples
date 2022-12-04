package io.devpl.spring.data.jpa.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity(name = "t_data_source_information")
@Table(name = "t_data_source_information")
public class DataSourceInformation {

    @Id
    @Column(name = "'ID'")
    private Long id;

    @Column(name = "NAME", unique = true)
    private String name;

    @Column(name = "TYPE", unique = true)
    private String type;

    @Column(name = "PROTOCOL", unique = true)
    private String protocol;

    @Column(name = "IP_ADDR", unique = true)
    private String ipAddr;

    @Column(name = "PORT", unique = true)
    private int port;
}

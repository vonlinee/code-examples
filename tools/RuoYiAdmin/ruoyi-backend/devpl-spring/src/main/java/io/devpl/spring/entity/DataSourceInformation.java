package io.devpl.spring.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "t_data_source_information")
@Data
public class DataSourceInformation {

    @Id
    @Column(name = "'ID'")
    private Long id;

    @Column(name = "NAME", unique = true)
    private String name;

    private String type;

}

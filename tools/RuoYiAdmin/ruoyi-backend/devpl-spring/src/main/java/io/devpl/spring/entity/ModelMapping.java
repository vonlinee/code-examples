package io.devpl.spring.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "t_model_info")
@Data
public class ModelMapping {

    @Id
    @Column(name = "'AAA'")
    private Long pid;

    @Column(name = "'MODEL_ID'", unique = true)
    private String modelId;
}

package com.tuling.dynamic.datasource.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="frend")
public class Frend  {
	
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 32)
    private Long id;
	
	@Column(name = "NAME")
    private String name;
}
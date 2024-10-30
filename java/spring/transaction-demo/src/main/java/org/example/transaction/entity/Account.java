package org.example.transaction.entity;

import lombok.Data;

/**
 * 账户的实体类
 */
@Data
public class Account {

    private Integer id;
    private String name;
    private Float money;
}

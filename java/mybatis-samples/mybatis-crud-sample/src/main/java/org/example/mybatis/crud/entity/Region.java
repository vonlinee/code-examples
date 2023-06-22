package org.example.mybatis.crud.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Region implements Serializable {

    private Integer id;

    /**
     * 区划码
     */
    private String regionCode;

    /**
     * 城市名称
     */

    private String regionName;

    /**
     * 父级区划码
     */
    private String parentCode;

    /**
     * 子集
     */

    private List<Region> children;
}


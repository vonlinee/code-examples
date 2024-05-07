package org.example.mybatis.crud.param;

import lombok.Data;

import java.util.List;

@Data
public class ListParam {

    private String sex;

    private List<String> studentIds;
}

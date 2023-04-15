package io.devpl.codegen.utils;

import lombok.Data;

import java.util.List;

@Data
public class TaskVersionLog {

    private String id;
    String superId;
    String name;

    List<TaskVersionLog> list;
}

package io.devpl.sdk.internal.rest;

import java.util.List;

public class TestEnumPool {


    public static void main(String[] args) {
        List<StatusCode> statuses = StatusCode.listAll();
        System.out.println(statuses);
        StatusCode.update(300, "重定向", true);
        statuses = StatusCode.listAll();
        System.out.println(statuses);

    }
}

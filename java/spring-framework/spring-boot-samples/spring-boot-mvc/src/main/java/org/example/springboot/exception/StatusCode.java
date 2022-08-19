package org.example.springboot.exception;

public enum StatusCode {

    //"正常"
    NORMAL(0, "正常"),

    //"无数据"
    NO_DATA(1, "无数据"),

    //"系统不可用"
    DENIED_SYSTEM(2, "系统不可用"),

    //"安全验证失败"
    FAILED_SECURE(3, "安全验证失败"),

    //"参数错误",
    FAILED_PARAMETER(4, "参数错误"),

    //"服务器异常"
    FAILED(5, "服务器异常"),

    //其他异常
    ELSE(6, "其他异常");


    private StatusCode(int key, String name) {
        this.key = key;
        this.name = name;
    }

    int key;
    String name;

    public int getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public static String getName(int key) {
        for (StatusCode e : StatusCode.values()) {
            if (e.getKey() == key) {
                return e.getName();
            }

        }
        return "";
    }
}

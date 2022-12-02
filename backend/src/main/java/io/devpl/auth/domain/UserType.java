package io.devpl.auth.domain;

/**
 * 用户类型枚举，与基础平台相同
 * 序列化输出为枚举类型的英文名
 * 0-学校管理员；1-教师；2-学生；3-家长；6-平台管理员，8-领导，9-监事员，10-机构用户
 */
public enum UserType {
    ADMIN(6, "超级管理员"), // 暂时使用领导当审查员
    REVIEWER(8, "审查员"), AGENCY(10, "机构用户"), OTHER(-1, "其他");

    private final int code;

    private final String desc;

    UserType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static UserType parse(int type) {
        switch (type) {
            case 6:
                return ADMIN;
            case 8:
                return REVIEWER;
            case 10:
                return AGENCY;
            default:
                return null;
        }
    }
}

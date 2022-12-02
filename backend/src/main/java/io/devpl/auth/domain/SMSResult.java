package io.devpl.auth.domain;

/**
 * 短信发送结果枚举
 */
public enum SMSResult {

    SUCCESS(1, "验证码已发送"),
    SERVICE_DOWN(2, "短信服务异常"),
    FAIL(3, "验证码发送失败"),
    WAIT(4, "您的操作过于频繁，请稍候再试");  // 针对同一手机号连续发送短信的时间间隔未超过配置时间

    private final int key;
    private final String name;

    SMSResult(int key, String name) {
        this.key = key;
        this.name = name;
    }

    public int getKey() {
        return key;
    }

    public String getName() {
        return name;
    }
}

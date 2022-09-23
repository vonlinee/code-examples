package io.devpl.sdk.restful;

public abstract class ResultfulResultTemplate extends ResultTemplate {

    /**
     * 状态码，不为空
     */
    protected int code;

    /**
     * 接口返回的信息
     */
    protected String message;

    /**
     * 用户界面的提示信息
     */
    protected String toast;

    /**
     * 访问的接口地址，如果不做特殊处理，这个值一般是拿不到的
     */
    protected String url;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getToast() {
        return toast;
    }

    public String getUrl() {
        return url;
    }
}

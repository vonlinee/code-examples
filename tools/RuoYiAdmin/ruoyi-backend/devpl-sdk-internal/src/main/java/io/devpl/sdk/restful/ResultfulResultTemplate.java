package io.devpl.sdk.restful;

/**
 * MVC规范
 * 1.C：控制层Controller,接口层，负责对请求的url分发到不同的网址，处理请求的入口。
 * 2.M：规范数据数据成Bean，并负责调用数据库
 * 3.V：只负责从数据库获取数据，并显示。
 * Controller负责调度View层和Model层，主要接收请求，然后转发到Model处理，处
 * <p>
 * 三层架构：实际上将Controller-Model层细分成三层
 * @since 0.0.1
 */
public abstract class ResultfulResultTemplate extends ResultTemplate {

    /**
     * HTTP状态码，不能为空，必须和HTTP header中的状态码一致
     */
    protected int code;

    /**
     * 对错误信息的简单解释
     */
    protected String message;

    /**
     * 用户提示信息，如果没有一定不要设置此值，避免造成混乱
     */
    protected String toast;

    /**
     * 对错误信息的详细解释的网址。比如可以包含，如果获取相应的权限等
     */
    protected String moreInfo;

    /**
     * 访问的接口地址
     */
    private String url;

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

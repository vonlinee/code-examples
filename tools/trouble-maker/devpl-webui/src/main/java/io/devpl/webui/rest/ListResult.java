package io.devpl.webui.rest;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 列表结果模板Builder
 *
 * @param <T>
 */
public class ListResult<T> extends ResultTemplate.Builder {

    private PageInfo pageInfo;

    private List<T> data;

    public static <T> ListResult<T> builder() {
        return new ListResult<>();
    }

    public ListResult<T> status(int code, String message) {
        this.code = code;
        this.message = message;
        return this;
    }

    public ListResult<T> status(ResponseStatus<Integer> status) {
        this.code = status.getCode();
        this.message = status.getMessage();
        return this;
    }

    public ListResult<T> pageInfo(int pageIndex, int pageSize) {
        if (this.pageInfo == null) this.pageInfo = new PageInfo();
        pageInfo.setPageIndex(pageIndex);
        pageInfo.setPageSize(pageSize);
        return this;
    }

    public ListResult<T> description(String description) {
        this.description = description;
        return this;
    }

    /**
     * 设置异常调用栈
     *
     * @param throwable
     * @param update    为true时才会进行更新
     * @return
     */
    public ListResult<T> throwable(Throwable throwable, boolean update) {
        if (!Result.STACK_TRACE_ENABLED) {
            return this;
        }
        if (stackTrace == null || stackTrace.length() == 0) {
            stackTrace = Result.throwToStr(throwable);
        }
        if (stackTrace != null && stackTrace.length() != 0 && update) {
            stackTrace = Result.throwToStr(throwable);
        }
        return this;
    }

    /**
     * 每次调用都会直接进行替换
     *
     * @param throwable
     * @return
     */
    public ListResult<T> throwable(Throwable throwable) {
        this.stackTrace = Result.throwToStr(throwable);
        return this;
    }

    public ListResult<T> data(List<T> data) {
        this.data = data;
        return this;
    }

    public List<T> getData() {
        return data;
    }

    @Override
    public ResultTemplate build() {
        ListResultTemplate template = new ListResultTemplate();
        template.code = this.code;
        template.message = this.message;
        template.setData(this.data);
        template.setPageInfo(this.pageInfo);
        template.setStacktrace(this.stackTrace);
        template.setDescription(this.description);
        return template;
    }
}

package io.devpl.codegen.mbpg.config;

/**
 * 注入全局Context实例
 */
public interface ContextAware {

    /**
     * 初始化该对象
     *
     * @param context 上下文实例
     */
    void setContext(Context context);
}

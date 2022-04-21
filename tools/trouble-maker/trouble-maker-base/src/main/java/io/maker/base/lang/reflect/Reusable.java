package io.maker.base.lang.reflect;

/**
 * 数据对象是否可重用，只是约定上可重用，依据子类实现是否遵守重用规则
 */
public interface Reusable {
    boolean reusable();
}

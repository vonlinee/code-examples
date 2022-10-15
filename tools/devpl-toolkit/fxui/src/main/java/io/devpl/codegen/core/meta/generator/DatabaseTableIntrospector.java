package io.devpl.codegen.core.meta.generator;

import io.devpl.codegen.core.meta.IntrospectedTable;

/**
 * 数据库表逆向
 * @param <R>
 */
public interface DatabaseTableIntrospector<R> {

    /**
     * 对数据库表进行处理，得到想要的结果
     * @param table
     * @return
     */
    R introspect(IntrospectedTable table);
}

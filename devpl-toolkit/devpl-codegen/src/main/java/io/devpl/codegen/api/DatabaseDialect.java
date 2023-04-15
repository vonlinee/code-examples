package io.devpl.codegen.api;

public interface DatabaseDialect {

    /**
     * 是否是关键字
     * @param identifier 标识符
     * @return 是否是关键字
     */
    boolean isKeyword(String identifier);
}

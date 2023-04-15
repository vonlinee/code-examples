package io.devpl.codegen.api.gen.template;

import java.util.Map;

/**
 * 模板参数，类似于实体类，将数据转换为Map以兼容多种模板引擎
 * 尽量将模板参数在模板渲染之前准备好，减少模板中的逻辑操作,视图与逻辑分离
 */
public interface TemplateArguments {

    /**
     * 将所有模板参数放进一个Map内
     * @return Map
     */
    Map<String, Object> asMap();
}

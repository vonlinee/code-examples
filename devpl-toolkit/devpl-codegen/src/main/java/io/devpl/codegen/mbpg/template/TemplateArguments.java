package io.devpl.codegen.mbpg.template;

import io.devpl.codegen.mbpg.config.Context;
import io.devpl.codegen.mbpg.config.ContextAware;
import io.devpl.codegen.mbpg.config.PropertyHolder;
import io.devpl.codegen.mbpg.config.po.IntrospectedTable;

import java.util.Map;

/**
 * 模板参数，类似于实体类，将数据转换为Map以兼容多种模板引擎
 * 尽量将模板参数在模板渲染之前准备好，减少模板中的逻辑操作
 * 视图与逻辑分离
 */
public abstract class TemplateArguments extends PropertyHolder implements ContextAware {

    private Context context;

    public abstract Map<String, Object> calculateArgumentsMap(IntrospectedTable tableInfo);

    @Override
    public void setContext(Context context) {
        this.context = context;
    }
}

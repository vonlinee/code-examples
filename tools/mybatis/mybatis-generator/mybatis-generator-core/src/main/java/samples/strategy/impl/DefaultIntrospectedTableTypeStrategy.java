package samples.strategy.impl;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.codegen.mybatis3.IntrospectedTableMyBatis3Impl;
import org.mybatis.generator.codegen.mybatis3.IntrospectedTableMyBatis3SimpleImpl;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.internal.ObjectFactory;
import org.mybatis.generator.runtime.dynamic.sql.IntrospectedTableMyBatis3DynamicSqlImpl;
import org.mybatis.generator.runtime.kotlin.IntrospectedTableKotlinImpl;
import samples.strategy.IntrospectedTableStrategy;

import static org.mybatis.generator.internal.util.StringUtils.isNotEmpty;

public class DefaultIntrospectedTableTypeStrategy implements IntrospectedTableStrategy {
    @Override
    public IntrospectedTable createIntrospectedTableForValidation(Context context) {
        String type = context.getTargetRuntime();
        if (!isNotEmpty(type)) {
            type = IntrospectedTableMyBatis3DynamicSqlImpl.class.getName();
        } else if ("MyBatis3".equalsIgnoreCase(type)) { //$NON-NLS-1$
            type = IntrospectedTableMyBatis3Impl.class.getName();
        } else if ("MyBatis3Simple".equalsIgnoreCase(type)) { //$NON-NLS-1$
            type = IntrospectedTableMyBatis3SimpleImpl.class.getName();
        } else if ("MyBatis3DynamicSql".equalsIgnoreCase(type)) { //$NON-NLS-1$
            type = IntrospectedTableMyBatis3DynamicSqlImpl.class.getName();
        } else if ("MyBatis3Kotlin".equalsIgnoreCase(type)) { //$NON-NLS-1$
            type = IntrospectedTableKotlinImpl.class.getName();
        }
        IntrospectedTable answer = (IntrospectedTable) ObjectFactory.createInternalObject(type);
        answer.setContext(context);
        return answer;
    }
}

package samples.strategy;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.config.Context;

public interface IntrospectedTableStrategy {

    /**
     * 依据上下文生成IntrospectedTable实例
     * @param context
     * @return
     */
    IntrospectedTable createIntrospectedTableForValidation(Context context);
}

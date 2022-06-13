package samples.plugins;

import org.mybatis.generator.api.PluginAdapter;

import java.util.List;

/**
 * 生成Eauals和hashCode方法
 */
public class EqualsAndHashCodePlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }
}

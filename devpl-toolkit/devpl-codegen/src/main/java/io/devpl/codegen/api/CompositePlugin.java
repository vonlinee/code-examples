package io.devpl.codegen.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * This class implements a composite plugin. It contains a list of plugins for the
 * current context and is used to aggregate plugins together. This class
 * implements the rule that if any plugin returns "false" from a method, then no
 * subsequent plugin is called.
 */
public abstract class CompositePlugin implements Plugin {
    private final List<Plugin> plugins = new ArrayList<>();

    protected CompositePlugin() {
        super();
    }

    public void addPlugin(Plugin plugin) {
        plugins.add(plugin);
    }

    @Override
    public void setContext(Context context) {
        for (Plugin plugin : plugins) {
            plugin.setContext(context);
        }
    }

    @Override
    public void setProperties(Properties properties) {
        for (Plugin plugin : plugins) {
            plugin.setProperties(properties);
        }
    }

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        for (Plugin plugin : plugins) {
            plugin.initialized(introspectedTable);
        }
    }
}

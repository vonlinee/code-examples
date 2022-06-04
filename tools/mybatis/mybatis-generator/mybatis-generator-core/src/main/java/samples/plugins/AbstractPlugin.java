package samples.plugins;

import org.mybatis.generator.api.Plugin;

public abstract class AbstractPlugin implements Plugin {

    protected boolean enabled;

    public final void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public final boolean isEnabled() {
        return enabled;
    }
}

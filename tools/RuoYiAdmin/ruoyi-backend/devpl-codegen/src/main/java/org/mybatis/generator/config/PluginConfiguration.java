package org.mybatis.generator.config;

import static org.mybatis.generator.internal.util.StringUtils.hasLength;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.util.List;

public class PluginConfiguration extends TypedPropertyHolder {
    public PluginConfiguration() {
        super();
    }

    public void validate(List<String> errors, String contextId) {
        if (!hasLength(getConfigurationType())) {
            errors.add(getString("ValidationError.17", //$NON-NLS-1$
                    contextId));
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PluginConfiguration) {
            PluginConfiguration pc = (PluginConfiguration) obj;
            return this.getConfigurationType().equals(pc.getConfigurationType());
        }
        return false;
    }
}

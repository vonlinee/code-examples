package org.mybatis.generator.config;

import static org.mybatis.generator.internal.util.StringUtils.hasLength;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.util.List;

public class JavaClientGeneratorConfiguration extends TypedPropertyHolder {
    private String targetPackage;
    private String targetProject;

    public JavaClientGeneratorConfiguration() {
        super();
    }

    public String getTargetProject() {
        return targetProject;
    }

    public void setTargetProject(String targetProject) {
        this.targetProject = targetProject;
    }

    public String getTargetPackage() {
        return targetPackage;
    }

    public void setTargetPackage(String targetPackage) {
        this.targetPackage = targetPackage;
    }

    public void validate(List<String> errors, String contextId) {
        if (!hasLength(targetProject)) {
            errors.add(getString("ValidationError.2", contextId)); //$NON-NLS-1$
        }

        if (!hasLength(targetPackage)) {
            errors.add(getString("ValidationError.12", //$NON-NLS-1$
                    "javaClientGenerator", contextId)); //$NON-NLS-1$
        }
    }
}

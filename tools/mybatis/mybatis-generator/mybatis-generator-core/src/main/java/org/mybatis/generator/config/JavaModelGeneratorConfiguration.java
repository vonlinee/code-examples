package org.mybatis.generator.config;

import static org.mybatis.generator.internal.util.StringUtils.isNotEmpty;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.util.List;

public class JavaModelGeneratorConfiguration extends PropertyHolder {

    private String targetPackage;

    private String targetProject;

    public JavaModelGeneratorConfiguration() {
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
        if (!isNotEmpty(targetProject)) {
            errors.add(getString("ValidationError.0", contextId)); //$NON-NLS-1$
        }

        if (!isNotEmpty(targetPackage)) {
            errors.add(getString("ValidationError.12", //$NON-NLS-1$
                    "JavaModelGenerator", contextId)); //$NON-NLS-1$
        }
    }
}

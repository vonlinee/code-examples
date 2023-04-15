package io.devpl.codegen.generator.template;

import org.mybatis.generator.exception.ShellException;

import java.io.File;

public class DefaultShellCallback implements ShellCallback {

    @Override
    public File getDirectory(String targetProject, String targetPackage) throws ShellException {
        return new File("D:/Temp");
    }

    @Override
    public boolean isOverwriteEnabled() {
        return true;
    }
}

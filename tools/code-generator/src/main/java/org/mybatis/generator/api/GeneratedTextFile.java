package org.mybatis.generator.api;

/**
 * 文本文件，常见的代码格式文件都是文本文件
 */
public abstract class GeneratedTextFile extends GeneratedFile {

    protected String fileExtensionName;

    protected GeneratedTextFile(String targetProject) {
        super(targetProject);
    }

    /**
     * Returns the entire contents of the generated file. Clients
     * can simply save the value returned from this method as the file contents.
     * Subclasses such as @see org.mybatis.generator.api.GeneratedJavaFile offer
     * more fine grained access to file parts, but still implement this method
     * in the event that the entire contents are desired.
     * @return Returns the content.
     */
    public abstract String getFormattedContent();

    public String getFileExtensionName() {
        return fileExtensionName;
    }
}

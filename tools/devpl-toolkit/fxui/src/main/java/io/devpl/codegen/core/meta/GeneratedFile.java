package io.devpl.codegen.core.meta;

public abstract class GeneratedFile {

    protected String filename;

    /**
     * 文件的项目位置
     */
    protected String targetProject;

    /**
     * Get the file name (without any path). Clients should use this method to
     * determine how to save the results.
     * @return Returns the file name.
     */
    public abstract String getFileName();

    public abstract String getAbsolutePath();
}

package io.devpl.codegen.api;

import java.nio.charset.StandardCharsets;

/**
 * Abstract class that holds information common to all generated files.
 */
public abstract class GeneratedFile {

    protected String path;

    /**
     * Returns the entire contents of the generated file. Clients
     * can simply save the value returned from this method as the file contents.
     * Subclasses such as @see org.mybatis.generator.api.GeneratedJavaFile offer
     * more fine-grained access to file parts, but still implement this method
     * in the event that the entire contents are desired.
     *
     * @return Returns the content.
     */
    public abstract String getFormattedContent();

    /**
     * Get the file name (without any path). Clients should use this method to
     * determine how to save the results.
     *
     * @return Returns the file name.
     */
    public abstract String getFileName();

    @Override
    public String toString() {
        return getFileName();
    }

    /**
     * Checks if is mergeable.
     *
     * @return true, if is mergeable
     */
    public abstract boolean isMergeable();

    /**
     * 获取文件编码
     *
     * @return 文件编码，默认UTF-8
     */
    public String getFileEncoding() {
        return StandardCharsets.UTF_8.name();
    }

    /**
     * 获取该文件应该生成的绝对路径
     *
     * @return
     */
    public String getAbsolutePath() {
        return path;
    }
}

package codegen;

import java.nio.charset.StandardCharsets;

public abstract class GeneratedTextFile extends GeneratedFile {

    /**
     * 文件的编码
     */
    protected String fileEncoding = StandardCharsets.UTF_8.name();

    public void setFileEncoding(String encoding) {
        this.fileEncoding = encoding;
    }

    public String getFileEncoding() {
        return fileEncoding;
    }

    public abstract String getFileExtensionName();
}

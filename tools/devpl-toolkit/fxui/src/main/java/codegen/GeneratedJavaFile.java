package codegen;

public class GeneratedJavaFile extends GeneratedTextFile {

    @Override
    public String getFileName() {
        return this.filename;
    }

    @Override
    public String getAbsolutePath() {
        return getFileName() + getFileExtensionName();
    }

    @Override
    public String getFileExtensionName() {
        return ".java";
    }
}

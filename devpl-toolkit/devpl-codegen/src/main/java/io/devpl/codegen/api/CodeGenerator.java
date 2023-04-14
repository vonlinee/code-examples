package io.devpl.codegen.api;

public interface CodeGenerator {

    /**
     * 开始生成
     *
     * @param progressCallback 进度回调
     */
    void generate(Context context, ProgressCallback progressCallback);
}

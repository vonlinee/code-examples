package io.devpl.codegen.mbpg.template;

import io.devpl.codegen.mbpg.ProgressCallback;

public abstract class CodeGenerator {

    /**
     * 开始生成
     *
     * @param progressCallback 进度回调
     */
    public abstract void generate(ProgressCallback progressCallback);
}

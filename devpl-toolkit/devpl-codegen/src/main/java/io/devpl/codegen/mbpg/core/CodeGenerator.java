package io.devpl.codegen.mbpg.core;

import io.devpl.codegen.mbpg.ProgressCallback;
import io.devpl.codegen.mbpg.config.Context;

public interface CodeGenerator {

    /**
     * 开始生成
     *
     * @param progressCallback 进度回调
     */
    void generate(Context context, ProgressCallback progressCallback);
}

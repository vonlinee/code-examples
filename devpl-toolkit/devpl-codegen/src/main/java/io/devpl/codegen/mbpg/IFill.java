package io.devpl.codegen.mbpg;

import io.devpl.codegen.mbpg.fill.FieldFill;
import org.jetbrains.annotations.NotNull;

/**
 * 填充接口
 */
public interface IFill {

    @NotNull String getName();

    @NotNull FieldFill getFieldFill();
}
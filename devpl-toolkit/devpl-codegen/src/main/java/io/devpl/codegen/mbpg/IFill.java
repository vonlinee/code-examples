package io.devpl.codegen.mbpg;

import io.devpl.codegen.mbpg.fill.FieldFill;

/**
 * 填充接口
 */
public interface IFill {

    String getName();

    FieldFill getFieldFill();
}

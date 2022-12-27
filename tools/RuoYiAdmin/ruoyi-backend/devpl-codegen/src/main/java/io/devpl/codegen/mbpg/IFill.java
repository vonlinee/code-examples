package io.devpl.codegen.mbpg;

import com.baomidou.mybatisplus.annotation.FieldFill;
import org.jetbrains.annotations.NotNull;

/**
 * 填充接口
 */
public interface IFill {

    @NotNull String getName();

    @NotNull FieldFill getFieldFill();
}

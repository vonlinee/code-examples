package io.devpl.codegen.mbpg;

import io.devpl.codegen.mbpg.config.po.TableInfo;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * 渲染模板接口
 * @author nieqiurong 2020/11/9.
 * @since 3.5.0
 */
public interface ITemplate {

    @NotNull
    Map<String, Object> renderData(@NotNull TableInfo tableInfo);
}

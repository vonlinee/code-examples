package io.maker.codegen.mbp;

import org.jetbrains.annotations.NotNull;

import io.maker.codegen.mbp.config.po.TableInfo;

import java.util.Map;

public interface ITemplate {

    @NotNull
    Map<String, Object> renderData(@NotNull TableInfo tableInfo);
}

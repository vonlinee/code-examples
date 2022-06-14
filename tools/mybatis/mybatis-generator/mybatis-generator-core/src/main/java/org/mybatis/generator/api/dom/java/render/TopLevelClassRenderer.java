package org.mybatis.generator.api.dom.java.render;

import static org.mybatis.generator.api.dom.java.render.DomRender.renderImports;
import static org.mybatis.generator.api.dom.java.render.DomRender.renderInnerClassNoIndent;
import static org.mybatis.generator.api.dom.java.render.DomRender.renderPackage;
import static org.mybatis.generator.api.dom.java.render.DomRender.renderStaticImports;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.mybatis.generator.api.dom.java.TopLevelClass;

/**
 * 渲染顶层类
 */
public class TopLevelClassRenderer {

    public String render(TopLevelClass topLevelClass) {
        List<String> lines = new ArrayList<>();
        lines.addAll(topLevelClass.getFileCommentLines());
        lines.addAll(renderPackage(topLevelClass));
        lines.addAll(renderStaticImports(topLevelClass));
        lines.addAll(renderImports(topLevelClass));
        // 无缩进
        lines.addAll(renderInnerClassNoIndent(topLevelClass, topLevelClass));
        return lines.stream().collect(Collectors.joining(System.getProperty("line.separator"))); //$NON-NLS-1$
    }
}

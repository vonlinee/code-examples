package org.mybatis.generator.api.dom.java.render;

import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.ArrayList;
import java.util.List;

import static org.mybatis.generator.api.dom.java.render.RenderingUtils.*;

public class TopLevelClassRenderer {

    private final String lineSeparator = System.getProperty("line.separator");

    public String render(TopLevelClass topLevelClass) {
        List<String> lines = new ArrayList<>();
        lines.addAll(topLevelClass.getFileCommentLines());
        lines.addAll(renderPackage(topLevelClass));
        lines.addAll(renderStaticImports(topLevelClass));
        lines.addAll(renderImports(topLevelClass));
        lines.addAll(renderInnerClassNoIndent(topLevelClass, topLevelClass));
        return String.join(lineSeparator, lines); //$NON-NLS-1$
    }
}

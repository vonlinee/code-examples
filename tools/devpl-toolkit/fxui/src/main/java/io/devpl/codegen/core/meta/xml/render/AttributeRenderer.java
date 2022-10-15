package io.devpl.codegen.core.meta.xml.render;

import io.devpl.codegen.core.meta.xml.Attribute;

public class AttributeRenderer {

    public String render(Attribute attribute) {
        return attribute.getName()
                + "=\"" //$NON-NLS-1$
                + attribute.getValue()
                + "\""; //$NON-NLS-1$
    }
}

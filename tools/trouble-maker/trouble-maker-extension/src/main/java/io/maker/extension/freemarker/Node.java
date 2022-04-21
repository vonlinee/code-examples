package io.maker.extension.freemarker;

import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

import java.util.List;

public class Node implements TemplateHashModel {

    private List<String> keys;
    private List<Node> nodes;

    public boolean isLeaf() {
        return nodes.isEmpty();
    }

    @Override
    public TemplateModel get(String key) throws TemplateModelException {
        return null;
    }

    @Override
    public boolean isEmpty() throws TemplateModelException {
        return false;
    }
}

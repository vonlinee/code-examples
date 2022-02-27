package io.maker.gui;

import java.util.List;

/**
 * 多行文本，不带有换行符
 */
public final class Lines {

    private List<String> lines;

    public static Lines of(List<String> lines) {
        Lines textLines = new Lines();
        textLines.setLines(lines);
        return textLines;
    }

    public List<String> getLines() {
        return lines;
    }

    public void setLines(List<String> lines) {
        this.lines = lines;
    }
}

package code.fxutils.core.fx.controller;

import java.util.List;

public interface HandleMethod {
    public Lines apply(Lines input);

    //去除空行
    HandleMethod removeBlankLines = input -> {
        List<String> lines = input.getLines();
        lines.removeIf(String::isBlank);
        return input;
    };
}

package io.maker.gui;

import com.google.common.base.CaseFormat;
import io.maker.base.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface HandleMethod {
    public Lines apply(Lines input);

    //去除空行
    HandleMethod removeBlankLines = input -> {
        List<String> lines = input.getLines();
        lines.removeIf(String::isEmpty);
        return Lines.of(lines);
    };

    //驼峰转下划线
    HandleMethod camelToUnderline = input -> {
        List<String> lines = input.getLines();
        for (String line : lines) {
            System.out.println(line);
            if (line.isEmpty()) {
                continue;
            }
            line = line.trim();
            line = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, line);
            System.out.println("    " + line);
        }
        return Lines.of(lines);
    };

    //下划线转驼峰
    HandleMethod underlineToCamel = input -> {
        List<String> lines = input.getLines();
        for (String line : lines) {
            if (line.isEmpty()) {
                lines.remove(line);
                continue;
            }
            line = line.trim();
            String regex = "_(.)";
            Matcher matcher = Pattern.compile(regex).matcher(line);
            while (matcher.find()) {
                String target = matcher.group(1);
                line = line.replaceAll("_" + target, target.toUpperCase());
            }
        }
        return Lines.of(lines);
    };

    //下划线转驼峰
    HandleMethod upperFirstCharacter = input -> {
        List<String> lines = input.getLines();
        List<String> output = new ArrayList<>();
        for (String line : lines) {
            if (line.isEmpty()) {
                continue;
            }
            System.out.println(line);
            line = line.trim();
            output.add(StringUtils.upperFirst(line));
        }
        return Lines.of(output);
    };
}

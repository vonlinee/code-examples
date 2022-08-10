package sample.java.utils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface HandleMethod {
    Lines apply(Lines input);

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
            if (line.isEmpty()) {
                continue;
            }
            line = line.trim();
            String regex = "([A-Z])";
            Matcher matcher = Pattern.compile(regex).matcher(line);
            while (matcher.find()) {
                String target = matcher.group();
                line = line.replaceAll(target, "_" + target.toLowerCase());
            }
        }
        return Lines.of(lines);
    };

    //下划线转驼峰
    HandleMethod underlineToCamel = input -> {
        List<String> lines = input.getLines();
        for (String line : lines) {
            if (line.isEmpty()) {
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
}

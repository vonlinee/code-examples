package io.maker.base.lang;

import java.util.regex.Pattern;

public class Regexs {

    public static boolean matches(Pattern pattern, String target) {
        return pattern.matcher(target).matches();
    }
}

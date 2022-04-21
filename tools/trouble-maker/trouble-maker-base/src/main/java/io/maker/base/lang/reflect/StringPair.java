package io.maker.base.lang.reflect;

public class StringPair extends Pair<String, String> {

    public StringPair(String key, String value) {
        super(key, value);
    }

    public String join(CharSequence delimiter) {
        return "";
    }
}

package io.devpl.codegen.jdbc.keywords;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import io.devpl.codegen.mbpg.config.IKeyWordsHandler;

public abstract class BaseKeyWordsHandler implements IKeyWordsHandler {

    public Set<String> keyWords;

    public BaseKeyWordsHandler(List<String> keyWords) {
        this.keyWords = new HashSet<>(keyWords);
    }

    public BaseKeyWordsHandler(Set<String> keyWords) {
        this.keyWords = keyWords;
    }

    @Override
    public Collection<String> getKeyWords() {
        return keyWords;
    }

    @Override
    public boolean isKeyWords(String columnName) {
        return getKeyWords().contains(columnName.toUpperCase(Locale.ENGLISH));
    }
}

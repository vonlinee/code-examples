package io.devpl.codegen.keywords;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.jetbrains.annotations.NotNull;

import io.devpl.codegen.mbpg.config.IKeyWordsHandler;

/**
 * 基类关键字处理
 *
 * @author nieqiurong 2020/5/8.
 * @since 3.3.2
 */
public abstract class BaseKeyWordsHandler implements IKeyWordsHandler {

    public Set<String> keyWords;

    public BaseKeyWordsHandler(@NotNull List<String> keyWords) {
        this.keyWords = new HashSet<>(keyWords);
    }

    public BaseKeyWordsHandler(@NotNull Set<String> keyWords) {
        this.keyWords = keyWords;
    }

    @Override
    public @NotNull Collection<String> getKeyWords() {
        return keyWords;
    }

    @Override
    public boolean isKeyWords(@NotNull String columnName) {
        return getKeyWords().contains(columnName.toUpperCase(Locale.ENGLISH));
    }
}

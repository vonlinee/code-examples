package io.devpl.logging.internal;

import org.apache.logging.log4j.spi.StandardLevel;
import org.apache.logging.log4j.util.Strings;

import java.util.Locale;

public class ExtendedLevel implements Level {

    private final int intLevel;
    private final String name;
    private final StandardLevel standardLevel;

    private ExtendedLevel(final String name, final int intLevel) {
        if (Strings.isEmpty(name)) {
            throw new IllegalArgumentException("Illegal null or empty Level name.");
        }
        if (intLevel < 0) {
            throw new IllegalArgumentException("Illegal Level int less than zero.");
        }
        this.name = name;
        this.intLevel = intLevel;
        this.standardLevel = StandardLevel.getStandardLevel(intLevel);
        if (LEVELS.putIfAbsent(name.trim().toUpperCase(Locale.ENGLISH), this) != null) {
            throw new IllegalStateException("Level " + name + " has already been defined.");
        }
    }

    @Override
    public String getName() {
        return name;
    }

    public StandardLevel getStandardLevel() {
        return standardLevel;
    }

    @Override
    public int intLevel() {
        return intLevel;
    }

    @Override
    @SuppressWarnings("unchecked")
    public ExtendedLevel getThis() {
        return this;
    }
}

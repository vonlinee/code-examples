package io.maker.base.lang.reflect;

import io.maker.base.lang.type.Value;

/**
 * bind a name to a specified value
 */
public final class NamedValue {

    private final String valueName;
    private Value value;

    private static final String UNKNOWN_NAME = "UNKNOWN_NAME";

    public NamedValue(String name, Object value) {
        this.value = Value.wrap(value);
        this.valueName = value == null ? "null" : name == null ? UNKNOWN_NAME : name;
    }

    public String getName() {
        return valueName;
    }

    @Override
    public String toString() {
        return valueName + " : " + value.get();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof NamedValue)) {
            return false;
        }
        final NamedValue namedValue = (NamedValue) obj;
        boolean nameEquals = this.valueName.equals(namedValue.valueName);
        if (value == null && namedValue.value == null)
            return nameEquals;
        else if (value != null && namedValue.value != null)
            return nameEquals & namedValue.value.equals(value);
        return false;
    }
}

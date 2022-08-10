package sample.java.collections.map;

import java.io.Serializable;

/**
 * @author vonline
 * @since 2022-07-31 19:02
 */
public final class MapBean implements Serializable {

    private volatile Field<Object>[] fields;

    public <V> V set(String fieldName, V val) {
        return set(fieldName, val, true);
    }

    private int hash() {
        return 0;
    }

    private <V> V set(String fieldName, Object val, boolean exceptionIfAbsent) {
        return null;
    }

    public <V> void addField(String fieldName, V val) {

    }

    private static class Field<V> implements Serializable {
        final int hash;
        final String fieldName;
        volatile V value;
        Field<V> next;

        Field(int hash, String fieldName, V val, Field<V> next) {
            this.hash = hash;
            this.fieldName = fieldName;
            this.value = val;
            this.next = next;
        }
    }

}

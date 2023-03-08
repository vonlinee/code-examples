package sample.java8.primary.generic;

public class GenericClass<K, V> {

    public V get(K key) {
        return null;
    }

    // Type parameter 'K' hides type parameter 'K'
    <K> K method2() {
        return null;
    }
}


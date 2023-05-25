package sample.java8.primary.generic;

import org.junit.jupiter.api.Test;

import java.lang.reflect.TypeVariable;
import java.util.HashMap;

public class TypeParameter<K, V> extends HashMap<K, V> {

    @Test
    public void test1() {
        TypeVariable<Class<TypeParameter>>[] typeParameters = TypeParameter.class.getTypeParameters();
        System.out.println(typeParameters.length);

        TypeParameter<String, Integer> tp = new TypeParameter<>();



    }
}

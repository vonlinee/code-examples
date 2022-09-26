package io.devpl.sdk;

/**
 * 将父类具体为子类的某个类型
 *
 * @param <C>
 * @param <P>
 */
public interface Materializable<C extends P, P> {

    C materialize(P parent);
}

package io.devpl.sdk.beans;

/**
 * A meta-bean that captures the type of the bean.
 * <p>
 * It is not possible to add the generic type to all beans, as the type cannot be
 * refined in hierarchies. This interface is thus useful when there are no subclasses.
 * @param <T> the type of the bean
 */
public interface TypedMetaBean<T extends Bean> extends MetaBean {

    @Override
    BeanBuilder<T> builder();

    @Override
    Class<T> beanType();
}

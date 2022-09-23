package io.devpl.sdk.internal;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 * the delegated list
 * @param <E> the type of element
 */
public interface DelegatedList<E> extends List<E> {

    /**
     * return the list to be delegated
     * @return not null
     */
    List<E> delegator();

    @Override
    default int size() {
        return delegator().size();
    }

    @Override
    default boolean isEmpty() {
        return delegator().isEmpty();
    }

    @Override
    default boolean contains(Object o) {
        return delegator().contains(o);
    }

    @Override
    default Iterator<E> iterator() {
        return delegator().iterator();
    }

    @Override
    default Object[] toArray() {
        return delegator().toArray();
    }

    @Override
    default <T> T[] toArray(T[] a) {
        return delegator().toArray(a);
    }

    @Override
    default boolean add(E e) {
        return delegator().add(e);
    }

    @Override
    default boolean remove(Object o) {
        return delegator().remove(o);
    }

    @Override
    default boolean containsAll(Collection<?> c) {
        return delegator().containsAll(c);
    }

    @Override
    default boolean addAll(Collection<? extends E> c) {
        return delegator().addAll(c);
    }

    @Override
    default boolean addAll(int index, Collection<? extends E> c) {
        return delegator().addAll(index, c);
    }

    @Override
    default boolean removeAll(Collection<?> c) {
        return delegator().removeAll(c);
    }

    @Override
    default boolean retainAll(Collection<?> c) {
        return delegator().retainAll(c);
    }

    @Override
    default void replaceAll(UnaryOperator<E> operator) {
        delegator().replaceAll(operator);
    }

    @Override
    default void sort(Comparator<? super E> c) {
        delegator().sort(c);
    }

    @Override
    default void clear() {
        delegator().clear();
    }

    @Override
    default E get(int index) {
        return delegator().get(index);
    }

    @Override
    default E set(int index, E element) {
        return delegator().set(index, element);
    }

    @Override
    default void add(int index, E element) {
        delegator().add(index, element);
    }

    @Override
    default E remove(int index) {
        return delegator().remove(index);
    }

    @Override
    default int indexOf(Object o) {
        return delegator().indexOf(o);
    }

    @Override
    default int lastIndexOf(Object o) {
        return delegator().lastIndexOf(o);
    }

    @Override
    default ListIterator<E> listIterator() {
        return delegator().listIterator();
    }

    @Override
    default ListIterator<E> listIterator(int index) {
        return delegator().listIterator(index);
    }

    @Override
    default List<E> subList(int fromIndex, int toIndex) {
        return delegator().subList(fromIndex, toIndex);
    }

    @Override
    default Spliterator<E> spliterator() {
        return delegator().spliterator();
    }

    @Override
    default <T> T[] toArray(IntFunction<T[]> generator) {
        return delegator().toArray(generator);
    }

    @Override
    default boolean removeIf(Predicate<? super E> filter) {
        return delegator().removeIf(filter);
    }

    @Override
    default Stream<E> stream() {
        return delegator().stream();
    }

    @Override
    default Stream<E> parallelStream() {
        return delegator().parallelStream();
    }

    @Override
    default void forEach(Consumer<? super E> action) {
        delegator().forEach(action);
    }
}

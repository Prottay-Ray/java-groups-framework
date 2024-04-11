package org.groupframework.group;

import java.util.*;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface Group<R> extends Iterable<R> {

    int size();

    boolean isEmpty();

    boolean contains(Object k);

    boolean containsAll(Group<?> k);

    Iterator<R> iterator();

    boolean add(R element);

    boolean remove(Object element);

    boolean addAll(Group<? extends R> elements);

    boolean removeAll(Group<?> elements);

    boolean retainAll(Group<?> c);

    void clear();

    boolean equals(Object o);

    int hashCode();

    Object[] toArray();

    <T> T[] toArray(T[] a);

    default <T> T[] toArray(IntFunction<T[]> generator) {
        return toArray(generator.apply(0));
    }

    default Stream<R> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    default Stream<R> parallelStream() {
        return StreamSupport.stream(spliterator(), true);
    }

    // Different from Group as here it tells the number of elements removed
    default int removeIf(Predicate<? super R> filter) {
        int removed = 0;
        Objects.requireNonNull(filter);
        final Iterator<R> each = iterator();
        while (each.hasNext()) {
            if (filter.test(each.next())) {
                each.remove();
                removed++;
            }
        }
        return removed;
    }
}
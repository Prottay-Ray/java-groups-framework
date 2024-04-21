package org.groupframework.group.sequence;

import org.groupframework.group.Group;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.function.UnaryOperator;

public interface Sequence<R> extends Group<R> {

    int size();

    boolean isEmpty();

    boolean add(R element);

    boolean remove(Object element);

    boolean addAll(Group<? extends R> elements);

    boolean removeAll(Group<?> elements);

    boolean retainAll(Group<?> c);

    void clear();

    boolean addAll(int index, Group<? extends R> c);

    R get(int index);

    R set(int index, R element);

    void add(int index, R element);

    R remove(int index);

    int indexOf(Object o);

    int lastIndexOf(Object o);

    ListIterator<R> listIterator();

    ListIterator<R> listIterator(int index);

    List<R> subList(int fromIndex, int toIndex);

    boolean contains(Object k);

    boolean containsAll(Group<?> k);

    Iterator<R> iterator();

    boolean equals(Object o);

    int hashCode();

    default void replaceAll(UnaryOperator<R> operator) {
        Objects.requireNonNull(operator);
        final ListIterator<R> li = this.listIterator();
        while (li.hasNext()) {
            li.set(operator.apply(li.next()));
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    default void sort(Comparator<? super R> c) {
        Object[] a = this.toArray();
        Arrays.sort(a, (Comparator) c);
        ListIterator<R> i = this.listIterator();
        for (Object e : a) {
            i.next();
            i.set((R) e);
        }
    }
}
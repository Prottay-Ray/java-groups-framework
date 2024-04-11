package org.groupframework.group;

import org.groupframework.exception.OutOfBoundsException;
import org.groupframework.utils.GroupUtils;

import java.io.Serial;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.RandomAccess;

public class ArraySequence<R> implements Sequence<R>, FailFastGroup<R>, RandomAccess,
        Serializable, Cloneable {

    @Serial
    private static final long serialVersionUID = -6550531918397868278L;

    private static final int DEFAULT_CAPACITY = 10;
    private static final Object[] EMPTY_ELEMENTS = {};

    private transient Object[] elements = EMPTY_ELEMENTS;

    // This is maintained because the backing array can contain more values than the actual intended values
    private transient int size;

    // This tells the current length of the backing array
    private transient int capacity;

    private transient int modCount;

    public ArraySequence() {
        setCapacity(DEFAULT_CAPACITY);
    }

    public ArraySequence(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Negative capacity : " + capacity);
        }
        setCapacity(capacity == 0
                ? DEFAULT_CAPACITY
                : capacity);
    }

    public ArraySequence(Group<? extends R> group) {
        if (GroupUtils.isEmpty(group)) {
            setCapacity(DEFAULT_CAPACITY);
        } else {
            int groupSize = group.size();
            setCapacity(groupSize < DEFAULT_CAPACITY
                    ? DEFAULT_CAPACITY
                    : groupSize + 10); // groupSize + 10 logic to be modified and improved
            addAll(group);
        }
    }

    private void setCapacity(int capacity) {
        this.elements = new Object[this.capacity = capacity];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean add(R element) {
        return false;
    }

    public boolean remove(Object element) {
        return false;
    }

    public boolean addAll(Group<? extends R> elements) {
        return false;
    }

    public boolean removeAll(Group<?> elements) {
        return false;
    }

    public boolean retainAll(Group<?> c) {
        return false;
    }

    public void clear() {
        size = 0;
        elements = EMPTY_ELEMENTS;
        setCapacity(DEFAULT_CAPACITY);
    }

    public boolean addAll(int index, Group<? extends R> c) {
        return false;
    }

    @SuppressWarnings("unchecked")
    public R get(int index) {
        validateIndex(index);
        return (R) elements[index];
    }

    private void validateIndex(int index) {
        if (index < 0 || index >= size()) {
            throw new OutOfBoundsException(index);
        }
    }

    public R set(int index, R element) {
        validateIndex(index);

        return null;
    }

    public void add(int index, R element) {
        validateIndex(index);
    }

    public R remove(int index) {
        validateIndex(index);

        return null;
    }

    public int indexOf(Object o) {
        if (!isEmpty()) {
            int i = -1;
            for (R r : this) {
                i++;
                if (Objects.equals(r, o)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public int lastIndexOf(Object o) {
        return 0;
    }

    public ListIterator<R> listIterator() {
        return null;
    }

    public ListIterator<R> listIterator(int index) {
        return null;
    }

    public List<R> subList(int fromIndex, int toIndex) {
        validateIndex(fromIndex);
        validateIndex(toIndex);

        return null;
    }

    public boolean contains(Object k) {
        return false;
    }

    public boolean containsAll(Group<?> k) {
        return false;
    }

    public Iterator<R> iterator() {
        return null;
    }

    public Object[] toArray() {
        Object[] objectArray = new Object[size()];
        for (int i = 0; i < size(); i++) {
            objectArray[i] = get(i);
        }
        return objectArray;
    }

    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public int getModCount() {
        return modCount;
    }

    @Override
    public R getCursorElement(int index) {
        return get(index);
    }

    @Override
    public ArraySequence<R> clone() {
        try {
            ArraySequence clone = (ArraySequence) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
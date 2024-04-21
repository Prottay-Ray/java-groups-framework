package org.groupframework.group.sequence;

import org.groupframework.exception.OutOfBoundsException;
import org.groupframework.group.Group;
import org.groupframework.utils.GroupUtils;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public class ArraySequence<R> extends GenericAbstractSequence<R>
        implements Sequence<R>, RandomAccess, Serializable, Cloneable {

    @Serial
    private static final long serialVersionUID = -6550531918397868278L;

    private static final int DEFAULT_CAPACITY = 10;

    private static final Object[] EMPTY_ELEMENTS = {};

    private transient Object[] elements = EMPTY_ELEMENTS;

    // This is maintained because the backing array can contain more values than the actual intended values
    private transient int size;

    // This tells the current length of the backing array
    private transient int capacity;

    public ArraySequence() {
        initialize(DEFAULT_CAPACITY);
    }

    public ArraySequence(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Negative capacity : " + capacity);
        }
        initialize(capacity == 0
                ? DEFAULT_CAPACITY
                : capacity);
    }

    public ArraySequence(Group<? extends R> group) {
        if (GroupUtils.isEmpty(group)) {
            initialize(DEFAULT_CAPACITY);
        } else {
            int groupSize = group.size();
            initialize(groupSize < DEFAULT_CAPACITY
                    ? DEFAULT_CAPACITY
                    : groupSize + (groupSize >> 1));
            addAll(group);
        }
    }

    private void initialize(int capacity) {
        size = 0;
        this.elements = new Object[this.capacity = capacity];
    }

    public void ensureCapacity(int minCapacity) {
        if (minCapacity > capacity) {
            modify();
            Object[] elements = new Object[capacity = minCapacity];
            for (int i = 0; i < size; i++) {
                elements[i] = this.elements[i];
            }
            this.elements = elements;
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean add(R element) {
        modify();
        int index = incrementCursor();
        elements[index] = element;
        return true;
    }

    public boolean remove(Object element) {
        int index = indexOf(element);
        if (index < 0) {
            return false;
        }
        if (size() == 1) {
            clear();
            return true;
        }
        modify();
        for (int i = index + 1; i < size; i++) {
            elements[i - 1] = elements[i];
        }
        size--;
        return true;
    }

    public boolean addAll(Group<? extends R> elements) {
        if (GroupUtils.isNotEmpty(elements)) {
            for (R element : elements) {
                add(element);
            }
            return true;
        }
        return false;
    }

    public boolean removeAll(Group<?> elements) {
        if (GroupUtils.isNotEmpty(elements)) {
            for (Object element : elements) {
                remove(element);
            }
            return true;
        }
        return false;
    }

    public boolean retainAll(Group<?> c) {

        return false;
    }

    public void clear() {
        modify();
        elements = EMPTY_ELEMENTS;
        initialize(DEFAULT_CAPACITY);
    }

    public boolean addAll(int index, Group<? extends R> group) {
        validateIndex(index);
        if (group == null) {
            return false;
        }
        for (R k : group) {
            add(index, k);
        }
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
        modify();
        @SuppressWarnings("unchecked")
        R oldValue = (R) elements[index];
        elements[index] = element;
        return oldValue;
    }

    public void add(int index, R element) {
        modify();
        validateIndex(index);
        int lastIndex = incrementCursor();
        for (int i = lastIndex; i > index; i--) {
            elements[i] = elements[i - 1];
        }
        elements[index] = element;
    }

    public int incrementCursor() {
        expand();
        if (isEmpty() || elements == EMPTY_ELEMENTS) {
            initialize(DEFAULT_CAPACITY);
        }
        return size++;
    }

    public int decrementCursor() {
        if (--size == 0) {
            clear();
        }
        return size;
    }

    public void expand() {
        if (size >= capacity) {
            int newCapacity = capacity + (capacity >> 1);
            Object[] newElements = new Object[newCapacity];
            for (int i = 0; i < size(); i++) {
                newElements[i] = elements[i];
            }
            elements = newElements;
            capacity = newCapacity;
        }
    }

    @SuppressWarnings("unchecked")
    public R remove(int index) {
        modify();
        validateIndex(index);
        Object element = elements[index];
        for (int i = index + 1; i < size(); i++) {
            elements[i - 1] = elements[i];
        }
        decrementCursor();
        return (R) element;
    }

    public int indexOf(Object o) {
        for (int i = 0; i < size(); i++) {
            if (Objects.equals(elements[i], o)) {
                return i;
            }
        }
        return -1;
    }

    public int lastIndexOf(Object o) {
        for (int i = size() - 1; i >= 0; i--) {
            if (Objects.equals(elements[i], o)) {
                return i;
            }
        }
        return -1;
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
        return indexOf(k) >= 0;
    }

    public boolean containsAll(Group<?> k) {
        for (Object o : k) {
            if (indexOf(o) < 0) {
                return false;
            }
        }
        return true;
    }

    public Iterator<R> iterator() {
        return new FailFastIterator<>(structuralModificationCount, this);
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
    public R getCursorElement(int index) {
        return get(index);
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public ArraySequence<R> clone() {
        try {
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return (ArraySequence) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
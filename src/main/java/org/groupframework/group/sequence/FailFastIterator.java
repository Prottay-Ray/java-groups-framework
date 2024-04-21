package org.groupframework.group.sequence;

import org.groupframework.exception.OutOfBoundsException;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;

public class FailFastIterator<K> implements Iterator<K> {

    private final GenericAbstractSequence<K> failFastSequence;
    private transient int structuralModificationCount;
    private transient int cursor = 0;
    private transient int lastReturnedIndex = -1;

    protected FailFastIterator(int structuralModificationCount, GenericAbstractSequence<K> failFastSequence) {
        this.structuralModificationCount = structuralModificationCount;
        this.failFastSequence = failFastSequence;
    }

    @Override
    public boolean hasNext() {
        return cursor < failFastSequence.size();
    }

    @Override
    public K next() {
        validateElementPresence();
        return getNextElement();
    }

    /**
     * It is to work only once after a next call. Otherwise, its illegal
     */
    @Override
    public void remove() {
        validateNoStructuralModification();
        if (lastReturnedIndex < 0) {
            throw new IllegalStateException();
        }
        validateElementPresence();
        failFastSequence.remove(cursor);
        lastReturnedIndex = -1;
        structuralModificationCount = failFastSequence.getStructuralModificationCount();
    }

    @Override
    public void forEachRemaining(Consumer<? super K> action) {
        Objects.requireNonNull(action);
        while (hasNext()) {
            action.accept(getNextElement());
        }
    }

    public void validateNoStructuralModification() {
        if (failFastSequence.getStructuralModificationCount() != structuralModificationCount) {
            throw new ConcurrentModificationException("Sequence has been modified while iterating");
        }
    }

    private void validateElementPresence() {
        if (!hasNext()) {
            throw new NoSuchElementException("no more element");
        }
    }

    private K getNextElement() {
        validateNoStructuralModification();
        try {
            return failFastSequence.getCursorElement(lastReturnedIndex = cursor++);
        } catch (OutOfBoundsException e) {
            throw new ConcurrentModificationException();
        }
    }
}
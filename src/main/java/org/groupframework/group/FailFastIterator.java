package org.groupframework.group;

import java.util.Iterator;
import java.util.function.Consumer;

public class FailFastIterator<K> implements Iterator<K> {

    private final int modCount;
    private final FailFastGroup<K> failFastGroup;

    public FailFastIterator(int modCount, FailFastGroup<K> failFastGroup) {
        this.modCount = modCount;
        this.failFastGroup = failFastGroup;
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public K next() {
        return null;
    }

    @Override
    public void remove() {

    }

    @Override
    public void forEachRemaining(Consumer<? super K> action) {

    }
}
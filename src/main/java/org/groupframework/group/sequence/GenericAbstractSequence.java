package org.groupframework.group.sequence;

import org.groupframework.group.FailFast;
import org.groupframework.group.Group;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

/**
 * This is a generic abstract implementation of a sequence providing the skeletal behaviour of a list
 * Any other random access sequence can extend this for the basic functionality
 * and customize it to provide additional customized behaviour.
 */
public abstract class GenericAbstractSequence<K>
        extends FailFast<K>
        implements Sequence<K>, RandomAccess, Serializable, Cloneable {


    protected int getStructuralModificationCount() {
        return structuralModificationCount;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean add(K element) {
        return false;
    }

    @Override
    public boolean remove(Object element) {
        return false;
    }

    @Override
    public boolean addAll(Group<? extends K> elements) {
        return false;
    }

    @Override
    public boolean removeAll(Group<?> elements) {
        return false;
    }

    @Override
    public boolean retainAll(Group<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean addAll(int index, Group<? extends K> c) {
        return false;
    }

    @Override
    public K get(int index) {
        return null;
    }

    @Override
    public K set(int index, K element) {
        return null;
    }

    @Override
    public void add(int index, K element) {

    }

    @Override
    public K remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<K> listIterator() {
        return null;
    }

    @Override
    public ListIterator<K> listIterator(int index) {
        return null;
    }

    @Override
    public List<K> subList(int fromIndex, int toIndex) {
        return null;
    }

    @Override
    public boolean contains(Object k) {
        return false;
    }

    @Override
    public boolean containsAll(Group<?> k) {
        return false;
    }

    @Override
    public Iterator<K> iterator() {
        return new FailFastIterator<>(structuralModificationCount, this);
    }
}
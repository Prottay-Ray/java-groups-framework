package org.groupframework.group;

public abstract class FailFast<R> {

    protected transient int structuralModificationCount = Integer.MIN_VALUE;

    protected void modify() {
        structuralModificationCount++;
    }

    public abstract R getCursorElement(int index);

}
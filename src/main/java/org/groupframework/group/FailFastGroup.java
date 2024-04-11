package org.groupframework.group;

public interface FailFastGroup<R> {

    int getModCount();
    R getCursorElement(int index);

}
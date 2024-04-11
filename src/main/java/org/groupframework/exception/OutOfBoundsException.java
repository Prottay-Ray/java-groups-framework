package org.groupframework.exception;

public class OutOfBoundsException extends RuntimeException {

    public OutOfBoundsException(int index) {
        super("Index " + index + " out of bounds");
    }

    public OutOfBoundsException(String msg) {
        super(msg);
    }
}
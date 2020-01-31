package org.interlink.grouporder.core.exceptions;

public class NotModifiedException extends RuntimeException {
    public NotModifiedException(String message) {
        super(message);
    }
}
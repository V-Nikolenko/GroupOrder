package org.interlink.grouporder.core.exceptions;

public class GroupOrderLockedException extends RuntimeException {
    public GroupOrderLockedException(String message) {
        super(message);
    }
}

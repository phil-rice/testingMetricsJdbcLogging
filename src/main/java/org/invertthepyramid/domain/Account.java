package org.invertthepyramid.domain;

public class Account {

    private final long id;
    private long customerId;

    public Account(long id, long customerId) {
        this.id = id;
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return String.format("Account[id=%d, customerId='%s']", id, customerId);
    }

}
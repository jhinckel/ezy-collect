package com.ezy.collect.messaging;

public enum QueueType {

    FIFO,
    STANDARD;

    public static QueueType fromValue(String value) {
        for (QueueType type : QueueType.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        return STANDARD;
    }

}

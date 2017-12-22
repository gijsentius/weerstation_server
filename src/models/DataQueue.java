package models;

import exceptions.BufferOverflowPreventException;
import interfaces.DataItem;

import java.util.HashMap;
import java.util.LinkedList;

public class DataQueue {
    private String identifier;
    private LinkedList<DataItem> dataBuffer;
    private int minQueueLength;
    private int maxQueueLength;
    private int length;

    public DataQueue(String identifier, int minQueueLength, int maxQueueLength) {
        this.identifier = identifier;
        this.minQueueLength = minQueueLength;
        this.maxQueueLength = maxQueueLength;
        dataBuffer = new LinkedList<>();
        length = 0;
    }

    public void addItem(DataItem data) {
        dataBuffer.add(data);
        length++;
        if (length >= maxQueueLength) {
            throw new BufferOverflowPreventException();
        }
    }
}

package models;

import exceptions.BufferOverflowPreventException;
import interfaces.DataItem;
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

    public void update(DataItem data) throws BufferOverflowPreventException {
        dataBuffer.add(data);
        length++;
        if (length + 1 >= maxQueueLength) {
            LinkedList<DataItem> tempBuffer = new LinkedList<>();
            int counter = 0;
            while (counter < length - minQueueLength) {
                tempBuffer.add(dataBuffer.getFirst());  // add the first item
                dataBuffer.removeFirst();  // remove the first item
                counter++;
            }
            throw new BufferOverflowPreventException(tempBuffer);
        }
    }
}

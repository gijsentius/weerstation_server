package models;

import exceptions.BufferOverflowPreventException;
import interfaces.DataItem;

import java.util.HashMap;
import java.util.LinkedList;

public class DataQueueBuffer {
    HashMap<String, DataQueue> dataQueueBuffer;

    public DataQueueBuffer() {
         dataQueueBuffer = new HashMap<>();
    }

    /**
     * Function that updates all the data queue buffers
     * When an buffer throws a buffer_overflow exception
     * @param dataItems
     * @throws BufferOverflowPreventException
     */
    public synchronized void update(LinkedList<DataItem> dataItems) throws BufferOverflowPreventException {
        int overflowFlag = 0;
        LinkedList<DataItem> overflow = new LinkedList<>();
        for (DataItem di : dataItems) {
            try {
                DataQueue dataQueue = dataQueueBuffer.get(di.getIdentifier());
                dataQueue.update(di);
            } catch (BufferOverflowPreventException e) {
                overflow.addAll(e.getBuffer());
                overflowFlag = 1;
            }
        }
        if (overflowFlag == 1) {
            overflowFlag = 0;
            throw new BufferOverflowPreventException(overflow);
        }
    }
}

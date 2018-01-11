package models;

import exceptions.BufferOverflowPreventException;
import interfaces.DataItem;
import java.util.HashMap;
import java.util.LinkedList;

public class DataQueueBuffer {
    private HashMap<String, DataQueue> dataQueueBuffer;
    private int minQueueLength;
    private int maxQueueLength;

    /**
     *
     * @param minQueueLength
     * @param maxQueueLength
     */
    public DataQueueBuffer(int minQueueLength, int maxQueueLength) {
         dataQueueBuffer = new HashMap<>();
         this.minQueueLength = minQueueLength;
         this.maxQueueLength = maxQueueLength;
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
                String id = di.getIdentifier();
                if (dataQueueBuffer.containsKey(id)) {
                    DataQueue dataQueue = dataQueueBuffer.get(di.getIdentifier());
                    dataQueue.update(di);
                } else {
                    dataQueueBuffer.put(di.getIdentifier(), new DataQueue(id, minQueueLength, maxQueueLength, di));
                }
            } catch (BufferOverflowPreventException e) {
                overflow.addAll(e.getBuffer());
                overflowFlag = 1;
            }
        }
        if (overflowFlag == 1) {
            throw new BufferOverflowPreventException(overflow);
        }
    }
}

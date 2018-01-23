package models;

import exceptions.BufferOverflowPreventException;
import interfaces.DataItem;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import models.WeatherData;

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
                WeatherData Items = new WeatherData();
                String[] keys = {"TEMP", "DEWP","STP", "SLP", "VISIB", "WDSP", "PRCP", "SNDP", "FRSHTT",
                        "CLDC", "WNDDIR"};
                LinkedList<DataItem> tempbuffer = e.getBuffer();
                String datum = "";
                String station = "";
                String tijd = "";
                float totaalkey = 0.0f;
                for(String key: keys) {
                    System.out.println(key);
                    for (DataItem hoi : tempbuffer) {
                        HashMap tijdelijk = hoi.getData();
                        if(tijdelijk.get(key) != null) {
                            totaalkey += Float.parseFloat((String) tijdelijk.get(key));
                        }
                        datum = tijdelijk.get("DATE").toString();
                        station = tijdelijk.get("STN").toString();
                        tijd = tijdelijk.get("Time").toString();

                    }
                    int grote = tempbuffer.size();
                    totaalkey = totaalkey / tempbuffer.size();
                    Items.addItem("DATE", datum);
                    Items.addItem("STN",station);
                    Items.addItem("TIME", tijd);
                    Items.addItem(key, Float.toString(totaalkey));
                }
                overflow.add(Items);
                overflowFlag = 1;
            }
        }
        if (overflowFlag == 1) {
            throw new BufferOverflowPreventException(overflow);
        }
    }

    public HashMap getBuffer(){
        return dataQueueBuffer;
    }

}

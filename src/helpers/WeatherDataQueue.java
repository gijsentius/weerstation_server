package helpers;

/*
* This class will make a sliding window that is saved on the Pi.
* The window will contain 30 measurements of a station
* This will help check when there is a measurement
* 30 * 800 queues = 24000 measurements
 */

import interfaces.DataItem;
import models.DataFrame;
import models.DataFrameBuffer;

import java.util.HashMap;
import java.util.List;

public class WeatherDataQueue {
    // Key = STN (station) Value = List of measurements.
    HashMap<String, List> map = new HashMap<String,List>();
    private int queueLength;
    private DataFrameBuffer queuebuffer;

    public WeatherDataQueue(int queueLength){
        this.queueLength = queueLength;
        queuebuffer = new DataFrameBuffer(queueLength);
    }


//    public void updateHashMap(DataItem dataItem){
//        int station = Integer.parseInt((String) dataItem.get("stn"));
//
//    }

}

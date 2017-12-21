package helpers;

/*
* This class will make a sliding window that is saved on the Pi.
* The window will contain 30 measurements of a station
* This will help check when there is a measurement
* 30 * 800 queues = 24000 measurements
 */

import models.DataFrame;
import models.DataFrameBuffer;

public class WeatherDataQueue {

    private int queueLength;
    private DataFrameBuffer queuebuffer;

    public WeatherDataQueue(int queueLength){
        this.queueLength = queueLength;
        queuebuffer = new DataFrameBuffer(queueLength);
    }




}

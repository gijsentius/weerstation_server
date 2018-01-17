package helpers;

import interfaces.DataItem;
import models.DataQueue;
import models.DataQueueBuffer;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;

public class DataIntegrityChecker {

    private HashMap buffer; // is een hashmap -> buffer.get(stationsnummer)

    public DataIntegrityChecker(DataQueueBuffer buffer) {
        this.buffer = buffer.getBuffer();

    }
    /*
    * calcMissingMeasurement expects a String key in its parameter
    * The key is given by checkdata if the value of the key == 0
     */
    public float calcMissingMeasurement(String key, String identifier) {
        // dataqueuebuffer is een hashmap (met key=stations value is buffer)
        // buffer is weer een hashmap met (key = station, value = data)
        int count = 0;
        Float total = 0.0f;
        Float average = 0.0f;

        DataQueue dataQueue = (DataQueue) buffer.get(identifier);

        if (dataQueue != null){
            LinkedList<DataItem> data = dataQueue.getBuffer();
            int item_number = 0;
            for (DataItem di : data) {
                if (item_number <= 30) {
                    HashMap d = di.getData();
                    total += (Float) d.get(key);
                    item_number++;
                }
            }
            average = total/item_number;

        } else{
            return 0.0f;
            }
        return average;

    }

    // Deze moet verandert worden naar een gemiddelde van 30 voorgaande metingen
    // uitzoeken hoe we 30 voorgaande metingen uit de buffer krijgen

    public void checkData(DataItem dataItem) {
        HashMap items = dataItem.getData();
        String identifier = dataItem.getIdentifier();
        for (Object o : items.entrySet()) {
            Map.Entry pair = (Map.Entry) o;
            // pair is op dit moment linkedlist met key en values
            switch ((String)pair.getKey()){
                // temperature
                case "TEMP":
                    if (pair.getValue() != null){ // hier is gekozen om tijdelijk 0.0 te doen, om te testen.
                        pair.setValue((Float)calcMissingMeasurement("TEMP", identifier));
                    }
                    break;
                    // Zichtbaarheid in kilometers
                case "VISIB":
                    if (pair.getValue() == null){
                        pair.setValue((Float)calcMissingMeasurement("VISIB", identifier));
                    }
                    break;
                    // windsnelheid
                case "WDSP":
                    if (pair.getValue() == null){
                        pair.setValue((Float)calcMissingMeasurement("WDSP", identifier));
                    }
                    break;
                    // neerslag
                case "PRCP":
                    if (pair.getValue() == null){
                        pair.setValue((Float)calcMissingMeasurement("PRCP", identifier));
                    }
                    break;
                    // Sneeuw
                case "SNDP":
                    if (pair.getValue() == null){
                        pair.setValue((Float)calcMissingMeasurement("SNDP", identifier));
                    }
                    break;
                    // windrichting
                case "WNDDIR":
                    if (pair.getValue() == null){
                        pair.setValue((Float)calcMissingMeasurement("WNDDIR", identifier));
                    }
                    break;
            }
        }
        //                    pair.getKey() pair.getValue());
    }

}

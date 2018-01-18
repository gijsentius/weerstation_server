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
    private String calcMissingMeasurement(String key, String identifier, String value) {
        // dataqueuebuffer is een hashmap (met key=stations value is buffer)
        // buffer is weer een hashmap met (key = station, value = data)
        int count = 0;
        float total = 0.0f;
        Float average = 0.0f;

        DataQueue dataQueue = (DataQueue) buffer.get(identifier); // pak van de buffer alleen de data van bepaalt station

        if (dataQueue.getBuffer().size() >= 30){
            LinkedList<DataItem> data = dataQueue.getBuffer(); // Een linkedlist met (0, data, 1, data)
            int item_number = 0;
            for (DataItem di : data) { // Pak per keer de data van 0,1,2 etc...
                if (item_number <= 30) {
                    HashMap d = di.getData(); // pak de hashmap van data die hoort bij 0,1,2 etc...
                    total += Float.parseFloat((String)d.get(key)); // krijg de waarde die hoort bij de ingegeven key en tel deze op bij total
                    item_number++; // doe dit maximaal 30 keer
                }
            }
            average = total/item_number;
        } else {
            return value;
            }
        return average.toString();
    }

    private float getAverageValue(String key, String identifier){
        DataQueue dataQueue = (DataQueue) buffer.get(identifier);
        LinkedList<DataItem> data = dataQueue.getBuffer();
        int count = 0;
        float returnValue = 0.0f;
        for (DataItem di : data) {
            if (count < 30) {
                HashMap d = di.getData();
                returnValue += Float.parseFloat((String) d.get(key));
                count++;
            }
        }
        returnValue = returnValue/count;
        return returnValue;
    }

/*
@param verwacht een dataItem
Functie zet een goede waarde als de waarde mist of teveel afwijkt.
 */

    public void checkData(DataItem dataItem) {
        HashMap items = dataItem.getData();
        String identifier = dataItem.getIdentifier();
        for (Object o : items.entrySet()) {
            Map.Entry pair = (Map.Entry) o;
            // pair is op dit moment linkedlist met key en values
            switch ((String)pair.getKey()){
                // temperature
                case "TEMP":
                    if ((Float)pair.getValue() - getAverageValue("TEMP", identifier) > 2){ // hier is gekozen om tijdelijk 0.0 te doen, om te testen.
                        pair.setValue(calcMissingMeasurement("TEMP", identifier, (String)pair.getValue()));
                    }
                    break;
                    // Zichtbaarheid in kilometers
                case "VISIB":
                    if (pair.getValue() == null){
                        pair.setValue(calcMissingMeasurement("VISIB", identifier, (String)pair.getValue()));
                    }
                    break;
                    // windsnelheid
                case "WDSP":
                    if (pair.getValue() == null){
                        pair.setValue(calcMissingMeasurement("WDSP", identifier, (String)pair.getValue()));
                    }
                    break;
                    // neerslag
                case "PRCP":
                    if (pair.getValue() == null){
                        pair.setValue(calcMissingMeasurement("PRCP", identifier, (String)pair.getValue()));
                    }
                    break;
                    // Sneeuw
                case "SNDP":
                    if (pair.getValue() == null){
                        pair.setValue(calcMissingMeasurement("SNDP", identifier, (String)pair.getValue()));
                    }
                    break;
                    // windrichting
                case "WNDDIR":
                    if (pair.getValue() == null){
                        pair.setValue(calcMissingMeasurement("WNDDIR", identifier, (String)pair.getValue()));
                    }
                    break;
            }
        }
    }
}

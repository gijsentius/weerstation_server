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
        Float average;

        DataQueue dataQueue = (DataQueue) buffer.get(identifier); // pak van de buffer alleen de data van bepaalt station
        //if (dataQueue != null) {
            //System.out.println(dataQueue.getBuffer().size());
        //}
        if (dataQueue != null && dataQueue.getBuffer().size() >= 1){
            average = getAverageValue(key, identifier);
        } else {

            return value;
            }
        return average.toString();
    }

    private float getAverageValue(String key, String identifier){
        int count = 0;
        float total = 0.0f;
        float value = 0.0f;

        DataQueue dataQueue = (DataQueue) buffer.get(identifier);
        if (dataQueue != null) {
            LinkedList<DataItem> data = dataQueue.getBuffer();
            for (DataItem di : data) {
                if (count < 30) {
                    HashMap d = di.getData();
                    if(d.get(key) != null) {
                        total += Float.parseFloat((String) d.get(key));
                        count++;
                    }

                }
            }
            value = total / count;
        }
        return value;
    }

    private boolean checkPeak(String key, float currentValue, String station, float marge)
    {
        if (currentValue < getAverageValue(key, station)-marge || currentValue > getAverageValue(key, station)+marge)
            {
                return true;
            }
        else
            {
                return false;
            }

    }

   private void checkPacket(HashMap pair)
   {
       String[] keys = {"TEMP", "VISIB", "WDSP", "PRCP", "SNDP", "WNDDIR"};
       if (pair.size() != 14){
           for(String key : keys) {

               if(!pair.containsKey(key))
               {
                   pair.put(key, null);
               }
           }
       }

   }


/*
@param verwacht een dataItem
Functie zet een goede waarde als de waarde mist of teveel afwijkt.
 */

    public void checkData(DataItem dataItem) {
        HashMap items = dataItem.getData();
        checkPacket(items);
        String identifier = dataItem.getIdentifier();
        for (Object o : items.entrySet()) {
            Map.Entry pair = (Map.Entry) o;

            // pair is op dit moment linkedlist met key en values
            float marge = 0.3f;
            switch ((String)pair.getKey()){
                // temperature
                case "TEMP":
//                    float Gemiddelde = getAverageValue("TEMP", identifier);
//                    Float Huidige waarde = Float.parseFloat((String)pair.getValue());
//                    Onderstaande if statments bestaan uit bovenstaande code om de juiste waarden te pakken
                    if (checkPeak("TEMP", Float.parseFloat((String)pair.getValue()), identifier, marge)){
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

package helpers;

import interfaces.DataItem;
import models.DataQueueBuffer;

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
    public float calcMissingMeasurement(String key, String identifier){
        // dataqueuebuffer is een hashmap (met key=stations value is buffer)
        // buffer is weer een hashmap met (key = station, value = data)

        Object dataQueue = buffer.get(identifier); // nu krijg je dataqueue terug

        float placeholder = 3.6f;
        return placeholder;

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
                    if (pair.getValue() == null){
                        pair.setValue((Float)calcMissingMeasurement("TEMP", identifier));
                    }
                    // Dauwpunt
                case "DEWP":
                    if (pair.getValue() == null){
                        pair.setValue((Float)calcMissingMeasurement("DEWP", identifier));
                    }
                    // luchtdruk stationsniveau
                case "STP":
                    if (pair.getValue() == null){
                        pair.setValue((Float)calcMissingMeasurement("STP", identifier));
                    }
                    // luchtdruk op zeeniveau
                case "SLP":
                    if (pair.getValue() == null){
                        pair.setValue((Float)calcMissingMeasurement("SLP", identifier));
                    }
                    // Zichtbaarheid in kilometers
                case "VISIB":
                    if (pair.getValue() == null){
                        pair.setValue((Float)calcMissingMeasurement("VISIB", identifier));
                    }
                    // windsnelheid
                case "WDSP":
                    if (pair.getValue() == null){
                        pair.setValue((Float)calcMissingMeasurement("WDSP", identifier));
                    }
                    // neerslag
                case "PRCP":
                    if (pair.getValue() == null){
                        pair.setValue((Float)calcMissingMeasurement("PRCP", identifier));
                    }
                    // Sneeuw
                case "SNDP":
                    if (pair.getValue() == null){
                        pair.setValue((Float)calcMissingMeasurement("SNDP", identifier));
                    }
                    // bewolking
                case "CLDC":
                    if (pair.getValue() == null){
                        pair.setValue((Float)calcMissingMeasurement("CLDC", identifier));
                    }
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

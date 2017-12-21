package helpers;

import interfaces.DataItem;
import models.DataFrame;
import models.DataFrameBuffer;

import java.util.*;

public class DataIntegrityChecker {

    private DataFrameBuffer buffer;

    DataIntegrityChecker() {
        buffer = new DataFrameBuffer();
    }
/*
* calcMissingMeasurement expects a String key in its parameter
* The key is given by checkdata if the value of the key == 0
 */
    public float calcMissingMeasurement(String key){
        // onderstaande is een array van dictonaries
        DataFrame[] bufferedData = buffer.getBuffer();

        float placeholder = 3.6f;
        return placeholder;

    }

    // Deze moet verandert worden naar een gemiddelde van 30 voorgaande metingen
    // uitzoeken hoe we 30 voorgaande metingen uit de buffer krijgen

    public void checkData(DataItem dataItem) {
        HashMap items = dataItem.getData();
        for (Object o : items.entrySet()) {
            Map.Entry pair = (Map.Entry) o;
            // pair is op dit moment linkedlist met key en values
            switch ((String)pair.getKey()){
                // temperature
                case "TEMP":
                    if (pair.getValue() == null){
                        pair.setValue((Float)calcMissingMeasurement("TEMP"));
                    }
                // Dauwpunt
                case "DEWP":
                    if (pair.getValue() == null){
                        pair.setValue((Float)calcMissingMeasurement("DEWP"));
                    }
                // luchtdruk stationsniveau
                case "STP":
                    if (pair.getValue() == null){
                        pair.setValue((Float)calcMissingMeasurement("STP"));
                    }
                // luchtdruk op zeeniveau
                case "SLP":
                    if (pair.getValue() == null){
                        pair.setValue((Float)calcMissingMeasurement("SLP"));
                    }
                // Zichtbaarheid in kilometers
                case "VISIB":
                    if (pair.getValue() == null){
                        pair.setValue((Float)calcMissingMeasurement("VISIB"));
                    }
                // windsnelheid
                case "WDSP":
                    if (pair.getValue() == null){
                        pair.setValue((Float)calcMissingMeasurement("WDSP"));
                    }
                // neerslag
                case "PRCP":
                    if (pair.getValue() == null){
                        pair.setValue((Float)calcMissingMeasurement("PRCP"));
                    }
                // Sneeuw
                case "SNDP":
                    if (pair.getValue() == null){
                        pair.setValue((Float)calcMissingMeasurement("SNDP"));
                    }
                // bewolking
                case "CLDC":
                    if (pair.getValue() == null){
                        pair.setValue((Float)calcMissingMeasurement("CLDC"));
                    }
                // windrichting
                case "WNDDIR":
                    if (pair.getValue() == null){
                        pair.setValue((Float)calcMissingMeasurement("WNDDIR"));
                    }
                    break;
            }
        }
        //                    pair.getKey() pair.getValue());
    }
}

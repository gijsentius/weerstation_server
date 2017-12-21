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

    public void calcTemperature(){
        // onderstaande is een array van dictonaries
        DataFrame[] bufferedData = buffer.getBuffer();


    }


    public void checkData(DataItem dataItem) {
        HashMap items = dataItem.getData();
        int station = Integer.parseInt((String) items.get("stn"));
        for (Object o : items.entrySet()) {
            Map.Entry pair = (Map.Entry) o;
            // pair is op dit moment linkedlist met key en values
            // Hieronder is pair.getValue eerst omgezet van object naar string en dan van string naar float
            if ((pair.getKey() == "TEMP") && Float.parseFloat((String)pair.getValue()) > 0){
                // nu moeten we de pair.getValue aanpassen
                // Deze moet verandert worden naar een gemiddelde van 10 voorgaande metingen
                // uitzoeken hoe we 10 voorgaande metingen uit de buffer krijgen
            }
        }
        //                    pair.getKey() pair.getValue());
    }
}

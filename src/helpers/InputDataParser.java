package helpers;

import interfaces.DataItem;
import models.DataFrame;
import models.WeatherData;
import org.w3c.dom.Document;

import java.util.LinkedList;

public class InputDataParser {
//    private static final int NUMBER_OF_DATA_ITEMS = 10;  // 10 is chosen because each xml file contains 10

    public static DataFrame parse(String document) {
        int index = 0;
        LinkedList<DataItem> items = new LinkedList<>();
        DataFrame df = new DataFrame(items);
        return df;
    }

    /**
     * source: https://stackoverflow.com/questions/4734586/how-to-get-root-node-attributes-on-java
     * @param document
     * @return
     */
    public static DataFrame parse(Document document) {
        int index = 0;
        document.getDocumentElement().normalize();
        LinkedList<DataItem> items = new LinkedList<>();
        /*
        build a dataframe here
         */
        DataFrame df = new DataFrame(items);
        return df;
    }
}

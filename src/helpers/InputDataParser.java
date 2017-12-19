package helpers;

import interfaces.DataItem;
import models.WeatherData;
import org.w3c.dom.Document;

public class InputDataParser {
    private static final int NUMBER_OF_DATA_ITEMS = 10;  // 10 is chosen because each xml file contains 10

    public static DataItem[] parse(String document) {
        int index = 0;
        DataItem[] items = new WeatherData[NUMBER_OF_DATA_ITEMS];
        return items;
    }

    /**
     * source: https://stackoverflow.com/questions/4734586/how-to-get-root-node-attributes-on-java
     * @param document
     * @return
     */
    public static DataItem[] parse(Document document) {
        int index = 0;
        document.getDocumentElement().normalize();
        DataItem[] items = new WeatherData[NUMBER_OF_DATA_ITEMS];
        // build 10 weather data items
        return items;
    }
}

package helpers;

import interfaces.DBItem;
import models.WeatherData;
import org.w3c.dom.Document;

public class InputDataParser {
    private static final int NUMBER_OF_DATA_ITEMS = 10;

    public static DBItem[] parse(String document) {
        int index = 0;
        DBItem[] items = new WeatherData[NUMBER_OF_DATA_ITEMS];
        return items;
    }

    public static DBItem[] parse(Document document) {
        int index = 0;
        document.getDocumentElement().normalize();
        DBItem[] items = new WeatherData[NUMBER_OF_DATA_ITEMS];
        // build 10 weather data items
        return items;
    }
}

package helpers;

import interfaces.DataItem;
import models.WeatherData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.LinkedList;

public class InputDataParser {
//    private static final int NUMBER_OF_DATA_ITEMS = 10;  // 10 is chosen because each xml file contains 10

    /**
     * source: https://stackoverflow.com/questions/4734586/how-to-get-root-node-attributes-on-java
     * @param document
     * @return
     */
    public static LinkedList<DataItem> parse(Document document) {
        int index = 0;
        document.getDocumentElement().normalize();
        LinkedList<DataItem> items = new LinkedList<>();
        /*
        build a dataframe here
        */
        Element origin = document.getDocumentElement();
        NodeList weatherStations = origin.getChildNodes();
        for (int i=0;i<weatherStations.getLength();i++) {
            NodeList stationData = weatherStations.item(i).getChildNodes();
            WeatherData weatherData = new WeatherData();
            for (int j=0;j< stationData.getLength();j++) {
                if (stationData.item(j).getChildNodes().getLength() > 0) {
                    String value = stationData.item(j).getChildNodes().item(0).getNodeValue();
                    String name = stationData.item(j).getNodeName();
                    if (name.equals("STN")) weatherData.setStation(value); // set station if value is station
                    weatherData.addItem(name, !value.equals("") ? value : null);
                }
            }
            if (weatherData.getLength() > 0) {
                items.add(weatherData);
            }
        }
        /*
        first check the integrity of the files
         */
        return items;
    }
}

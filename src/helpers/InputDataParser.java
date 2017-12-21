package helpers;

import interfaces.DataItem;
import loggers.DataLogger;
import models.DataFrame;
import models.WeatherData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
        Element origin = document.getDocumentElement();
        NodeList weatherStations = origin.getChildNodes();
        for (int i=0;i<weatherStations.getLength();i++) {
            NodeList stationData = weatherStations.item(i).getChildNodes();
            WeatherData weatherData = new WeatherData();
            for (int j=0;j< stationData.getLength();j++) {
                if (stationData.item(j).getChildNodes().getLength() > 0) {
                    weatherData.addItem(stationData.item(j).getNodeName(), stationData.item(j).getChildNodes().item(0).getNodeValue());
                }
            }
            if (weatherData.getLength() > 0) {
                System.out.println(weatherData.getData().toString());
                items.add(weatherData);
            }
        }
        DataLogger.logData(items);
        /*
        first check the integrity of the files
         */
        return new DataFrame(items);
    }
}

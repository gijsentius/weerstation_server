package models;

import interfaces.DataItem;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * TODO add all the weather data subjects
 * TODO fix the data in a dictionary structure so the data handling can be done easily
 */
public class WeatherData implements DataItem {

    private HashMap<String, String > data;

    /**
     * Class that represents the weather data
     */
    public WeatherData() {
        data = new HashMap<>();
    }

    public void addItem(String name, String value) {
        data.put(name, value);
    }

    @Override
    public String[] getHeaders() {
        return null;
    }

    @Override
    public HashMap getData() {
        return this.data;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

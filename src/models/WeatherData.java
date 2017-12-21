package models;

import interfaces.DataItem;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;

public class WeatherData implements DataItem {

    private HashMap<String, String > data;
    private int length;

    /**
     * Class that represents the weather data
     */
    public WeatherData() {
        this.data = new HashMap<>();
        this.length = 0;
    }

    public void addItem(String name, String value) {
        data.put(name, value);
        this.length++;
    }

    public int getLength() {
        return this.length;
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

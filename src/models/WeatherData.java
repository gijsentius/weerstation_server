package models;

import interfaces.DataItem;

import java.util.HashMap;

public class WeatherData implements DataItem {

    private String station;
    private HashMap<String, String > data;
    private int length;

    /**
     * Class that represents the weather data
     */
    public WeatherData(String station) {
        this.station = station;
        this.data = new HashMap<>();
        this.length = 0;
    }

    public WeatherData() {
        this.data = new HashMap<>();
        this.length = 0;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public void addItem(String name, String value) {
        data.put(name, value);
        this.length++;
    }

    public int getLength() {
        return this.length;
    }

    @Override
    public String getIdentifier() {
        return station;
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

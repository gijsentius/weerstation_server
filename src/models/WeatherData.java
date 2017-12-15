package models;

import interfaces.DataItem;

import java.util.Dictionary;

/**
 * TODO add all the weather data subjects
 * TODO fix the data in a dictionary structure so the database handling can be done easily
 */
public class WeatherData implements DataItem {

    /**
     * Class that represents the weather data
     */
    public WeatherData() {

    }

    @Override
    public String[] getHeaders() {
        return new String[0];
    }

    @Override
    public Dictionary getData() {
        return null;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

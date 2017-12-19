package runnables;

import helpers.WeatherDatabase;
import helpers.WeatherFileStorage;
import models.DataFrame;

import java.sql.SQLException;

public class DataUpdateHandler implements Runnable {
    private WeatherDatabase weatherDatabase;
    private WeatherFileStorage weatherFileStorage;
    private DataFrame[] dataFrames;

    public DataUpdateHandler(DataFrame[] dataFrameBuffer, WeatherDatabase weatherDatabase) {
        weatherDatabase = weatherDatabase;
        dataFrames = dataFrameBuffer;
    }

    /**
     * depending on the data storage type a different constructor will be called
     * This wil be the constructor for the filesystem type
     * @param buffer
     */
    public DataUpdateHandler(DataFrame[] buffer, WeatherFileStorage weatherFileStorage) {

    }

    public DataUpdateHandler(DataFrame[] buffer) {

    }


    @Override
    public void run() {
        System.out.println("Wow the db update thread is running");
        try {
            weatherDatabase.updateDB(dataFrames);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

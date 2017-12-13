package runnables;

import helpers.WeatherDB;
import interfaces.DBConnector;
import models.DataFrame;
import models.WeatherData;

import java.sql.SQLException;

public class DataUpdateHandler implements Runnable {
    private WeatherDB weatherDB;
    private DataFrame[] dataFrames;

    public DataUpdateHandler(DataFrame[] dataFrameBuffer, WeatherDB dbConnector) {
        weatherDB = dbConnector;
        dataFrames = dataFrameBuffer;
    }

    /**
     * depending on the data storage type a different constructor will be called
     * This wil be the constructor for the filesystem type
     * @param buffer
     */
    public DataUpdateHandler(DataFrame[] buffer) {

    }

    @Override
    public void run() {
        System.out.println("Wow the db update thread is running");
        try {
            weatherDB.updateDB(dataFrames);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

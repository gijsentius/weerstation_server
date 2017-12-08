package runnables;

import helpers.WeatherDB;
import interfaces.DBConnector;
import models.DataFrame;
import models.WeatherData;

public class DBUpdateHandler implements Runnable {

    public DBUpdateHandler(DataFrame[] dataFrameBuffer, WeatherDB dbConnector) {

    }

    @Override
    public void run() {
        System.out.println("Wow the db update thread is running");
    }
}

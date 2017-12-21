package runnables;

import helpers.WeatherDatabase;
import helpers.WeatherFileStorage;
import interfaces.StorageHandler;
import loggers.ExceptionLogger;
import models.DataFrame;

import java.sql.SQLException;

public class DataUpdateHandler implements Runnable {
    private StorageHandler storageHandler;
    private DataFrame[] dataFrames;

    public DataUpdateHandler(DataFrame[] dataFrameBuffer, WeatherDatabase weatherDatabase) {
        storageHandler = weatherDatabase;
        dataFrames = dataFrameBuffer;
    }

    /**
     * depending on the data storage type a different constructor will be called
     * This wil be the constructor for the filesystem type
     * @param dataFrameBuffer
     */
    public DataUpdateHandler(DataFrame[] dataFrameBuffer, WeatherFileStorage weatherFileStorage) {
        storageHandler = weatherFileStorage;
        dataFrames = dataFrameBuffer;
    }

    public DataUpdateHandler(DataFrame[] dataFrameBuffer, StorageHandler storageHandler) {
        this.storageHandler = storageHandler;
        dataFrames = dataFrameBuffer;
    }

    @Override
    public void run() {
        try {
            storageHandler.update(dataFrames);
        } catch (NullPointerException e) {
            ExceptionLogger.logException(e);
        }
    }
}
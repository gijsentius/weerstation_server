package runnables;

import helpers.WeatherDatabase;
import helpers.WeatherFileStorage;
import interfaces.DataItem;
import interfaces.StorageHandler;
import loggers.ExceptionLogger;
import models.DataFrame;

import java.sql.SQLException;
import java.util.LinkedList;

public class DataUpdateHandler implements Runnable {
    private StorageHandler storageHandler;
    private LinkedList<DataItem> dataItems;

    public DataUpdateHandler(LinkedList<DataItem> dataItems, StorageHandler storageHandler) {
        this.dataItems = dataItems;
    }

    @Override
    public void run() {
        try {
            storageHandler.update(dataItems);
        } catch (NullPointerException e) {
            ExceptionLogger.logException(e);
        }
    }
}
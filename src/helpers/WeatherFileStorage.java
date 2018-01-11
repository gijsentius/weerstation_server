package helpers;

import interfaces.DataItem;
import interfaces.StorageHandler;

import java.util.LinkedList;

public class WeatherFileStorage implements StorageHandler{

    @Override
    public void update(LinkedList<DataItem> dataItems) {
        for (DataItem di: dataItems) {
            System.out.println(di.getIdentifier());
        }
    }
}

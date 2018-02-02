package helpers;

import interfaces.DataItem;
import interfaces.StorageHandler;
import loggers.ExceptionLogger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.time.LocalDate;

import java.util.LinkedList;
import java.util.Map;

public class WeatherFileStorage implements StorageHandler {

    @Override
    public void update(LinkedList<DataItem> dataItems) {
        String path = File.separator + ".." + File.separator + "nfs" + File.separator + "general" + File.separator;
        for (DataItem di : dataItems) {
            Map dataMap = di.getData();
            File file = new File(path + LocalDate.now().toString() + "_" + dataMap.get("STN").toString());
            if (file.exists()) // update the existing file
            {
                try {
                    JSONArray jsonArray = readDataFromFile(file);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.putAll(dataMap);
                    jsonArray.add(jsonObject);
                    writeDataToFile(jsonArray, file);
                } catch (IOException | ParseException e) {
                    ExceptionLogger.logException(e);
                }
            } else // make a new file
            {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.putAll(dataMap);
                    JSONArray jsonArray = new JSONArray();
                    jsonArray.add(jsonObject);
                    writeDataToFile(jsonArray, file);
                } catch (IOException e) {
                    ExceptionLogger.logException(e);
                }
            }
        }
    }

    private void writeDataToFile(JSONArray jsonArray, File file) throws IOException {
        FileWriter jsonFileWriter = new FileWriter(file);
        jsonFileWriter.write(jsonArray.toJSONString());
        jsonFileWriter.flush();
        jsonFileWriter.close();
    }

    private JSONArray readDataFromFile(File file) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        FileReader fileReader = new FileReader(file);
        return (JSONArray) parser.parse(fileReader);
    }
}
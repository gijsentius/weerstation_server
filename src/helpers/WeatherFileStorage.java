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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class WeatherFileStorage implements StorageHandler {

    @Override
    public void update(LinkedList<DataItem> dataItems) {
        Map listwithdata = new HashMap();
        String path = File.separator + ".." + File.separator + "nfs" + File.separator + "general" + File.separator;
        String foldername = "weerdata";

        for (DataItem di : dataItems) {
            listwithdata = di.getData();
            File file = new File(path + LocalDate.now().toString() + "_" + listwithdata.get("STN").toString());
            if (file.exists()) // update the existing file
            {

            } else // make a new file
            {

            }
        }
    }

    private void writeDataToFile(JSONArray jsonArray, File file) throws IOException {
        StringWriter out = new StringWriter();
        jsonArray.writeJSONString(out);
    }

    private JSONArray readDataFromFile(File file) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        FileReader fileReader = new FileReader(file);
        Object obj = (JSONObject) parser.parse(fileReader);
        JSONObject jsonObject = (JSONObject) obj;
        return (JSONArray) jsonObject.get(0);
    }
}
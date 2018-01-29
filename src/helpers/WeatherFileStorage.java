package helpers;

import interfaces.DataItem;
import interfaces.StorageHandler;
import org.json.simple.JSONArray;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class WeatherFileStorage implements StorageHandler{

    @Override
    public void update(LinkedList<DataItem> dataItems) {
        Map listwithdata = new HashMap();
        String foldername = "weerdata";

        File filefolder = new File(foldername);
        if (! filefolder.exists())
        {
            System.out.println("Folder missing creating new folder");
            filefolder.mkdir();
            System.out.println("Folder created");
        }

        //loopt door de ontvangen data heen
        for (DataItem di: dataItems) {
            //verandert de de dataitem naar een hashmap
            listwithdata = di.getData();

            File filenamelocation = new File(filefolder + "/" + LocalDate.now().toString() + "_" + listwithdata.get("STN").toString());
            //veranderd alle data in de hashmap naar een json array vervolgens wordt er gekeken of het bestand al bestaat.
            // wanneer deze bestaat wordt er data toegevoegd en anders een nieuw bestand aangemaakt met daar de data in.
            try (FileWriter file = new FileWriter(filenamelocation, true)) {
                System.out.println("trying to make a file");
                JSONArray lijstnaarjson = new JSONArray();
                lijstnaarjson.add(listwithdata);
                if (filenamelocation.exists())
                {
                    System.out.println("File " + listwithdata.get("STN") + " already exists.");
                    file.append(lijstnaarjson.toJSONString());
                    file.flush();
                }
                else
                {
                    file.write(lijstnaarjson.toJSONString());
                    file.flush();
                    System.out.println("File " + listwithdata.get("STN") + " created.");
                }
            } catch (IOException e) {
                System.out.println("failed");
                e.printStackTrace();
            }
        }
    }
}

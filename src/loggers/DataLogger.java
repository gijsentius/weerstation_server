package loggers;

import helpers.LogWriter;
import interfaces.DataItem;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;

public class DataLogger {
    public synchronized static void logData(LinkedList<DataItem> items) {
        String pathname = "data_log_" + LocalDate.now().toString() + ".txt";
        Path file = Paths.get(pathname);
        LinkedList<String> dataStrings = new LinkedList<>();
        dataStrings.add(LocalDateTime.now().toString());
        for (DataItem item : items) {
            dataStrings.add(item.getData().toString());
        }
        LogWriter.writeToLog(file, dataStrings);  // function to write data to a file
    }
}

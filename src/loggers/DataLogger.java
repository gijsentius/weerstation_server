package loggers;

import helpers.LogWriter;
import interfaces.DataItem;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class DataLogger {
    public synchronized static void logData(LinkedList<DataItem> items) {
        String pathname = "data_log_" + LocalDate.now().toString() + ".txt";
        Path file = Paths.get(pathname);
        LinkedList<String> dataStrings = new LinkedList<>();
        dataStrings.add(LocalDateTime.now().toString() + "\r\n");
        for (DataItem item : items) {
            dataStrings.add(item.getData().toString());
        }
        List<String> lines = dataStrings;  // List with the data per data_item as a string
        LogWriter.writeToLog(file, lines);  // function to write data to a file
    }
}

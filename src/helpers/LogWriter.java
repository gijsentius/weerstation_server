package helpers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class LogWriter {
    public static void writeToLog(Path file, List<String> data) {
        try {
            if (Files.exists(file)) {
                Files.write(file, data, StandardOpenOption.APPEND);
            } else {
                Files.write(file, data, StandardOpenOption.CREATE_NEW);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

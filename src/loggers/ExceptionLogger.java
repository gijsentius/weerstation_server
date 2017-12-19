package loggers;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;

/**
 * source: https://stackoverflow.com/questions/5175728/how-to-get-the-current-date-time-in-java
 * source: https://stackoverflow.com/questions/2885173/how-do-i-create-a-file-and-write-to-it-in-java
 * source: See mobile phone
 * writes an messages to a log file
 */
public class ExceptionLogger {
    public synchronized static void logException(Exception e) {
        Path file = Paths.get("error_log_" + LocalDateTime.now().toString() + ".txt");  // time is unique
        // get stacktrace from exception
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        byte[] data = sw.toString().getBytes();
        try {
            Files.write(file, data, StandardOpenOption.APPEND);  // Appends data to the file if it already exists
        } catch (IOException ex) {
            ExceptionLogger.logException(ex);  // TODO: check if this shows the stacktrace in the folder
        }
    }
}

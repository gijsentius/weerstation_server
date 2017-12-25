package loggers;

import helpers.LogWriter;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * source: https://stackoverflow.com/questions/5175728/how-to-get-the-current-date-time-in-java
 * source: https://stackoverflow.com/questions/2885173/how-do-i-create-a-file-and-write-to-it-in-java
 * source: See mobile phone
 * writes an messages to a log file
 */
public class ExceptionLogger {
    private static StringWriter sw;
    private static int buffer;  // buffer is just an integer counting towards a certain number
    private static int max_buffer;

    public ExceptionLogger(int max_buffer_size) {
        sw = new StringWriter();
        buffer = 0;
        max_buffer = max_buffer_size;
    }

    /**
     * This method can be considered slow because it opens and closes files constantly
     * @param e
     */
    public synchronized static void logException(Exception e) {
        String pathname = "error_log_" + LocalDate.now().toString() + ".txt";
        Path file = Paths.get(pathname);
        StringWriter sw = new StringWriter();
        sw.append(LocalDateTime.now().toString() + "\r\n");
        e.printStackTrace(new PrintWriter(sw)); // get stacktrace from exception
        String data = sw.toString();
        List<String> lines = Arrays.asList(data);
        LogWriter.writeToLog(file, lines);
    }

    /**
     * More efficient method of writing to a file
     * Build with help of the following web content
     * source: https://stackoverflow.com/questions/1625234/how-to-append-text-to-an-existing-file-in-java
     * source: https://stackoverflow.com/questions/1053467/how-do-i-save-a-string-to-a-text-file-using-java
     */
    public static synchronized void logExceptionsBuffered(Exception e) {
        String pathname = "error_log_" + LocalDate.now().toString() + ".txt";
        if (buffer > max_buffer) {
            try(  PrintWriter out = new PrintWriter( pathname )  ) {
                out.println(sw.toString());
                sw = new StringWriter();
                buffer = 0;
            } catch (FileNotFoundException e1) {
                e1.printStackTrace(new PrintWriter(sw));
                buffer++;
            }
        }
    }
}

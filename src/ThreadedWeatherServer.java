import helpers.WeatherFileStorage;
import interfaces.StorageHandler;
import loggers.ExceptionLogger;
import models.DataQueueBuffer;
import runnables.DataSocketHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Source: https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/ExecutorService.html
 */
public class ThreadedWeatherServer implements Runnable{
    private final ServerSocket serverSocket;
    private final ExecutorService pool;
    private DataQueueBuffer dataQueueBuffer;
    private StorageHandler storageHandler;

    public ThreadedWeatherServer(int port, int bufferSize, int poolSize) throws IOException, SQLException {
        serverSocket = new ServerSocket(port);
        pool = Executors.newFixedThreadPool(poolSize);
        dataQueueBuffer = new DataQueueBuffer(30, 30 + bufferSize); // because of extrapolation the buffer needs a minimum length of 30
        storageHandler = new WeatherFileStorage(); // type of storageHandler
    }

    @Override
    public void run() {
        try {
            while(true) {
                pool.execute(new DataSocketHandler(serverSocket.accept(), dataQueueBuffer, storageHandler));
            }
        } catch (IOException e) {
            ExceptionLogger.logException(e);
        }
        this.pool.shutdown(); // shutdown thread pool after all threads finished
    }

    public void terminate() {
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            // What do we catch here
            ExceptionLogger.logException(e);
        }
    }

    public static void main(String [ ] args)
    {
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        System.out.println("Enter the port you want to use: ");
        int port = reader.nextInt(); // Scans the next token of the input as an int.
        //once finished
        reader.close();
        System.out.println("We are using port: " + port);
        try {
            new ThreadedWeatherServer(port, 30, 800).run();
        } catch (IOException | SQLException e) {
//                ts.terminate();  // maybe a more subtle solution can be found for the termination of the socket
            ExceptionLogger.logException(e);
        }
    }
}

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
public class ThreadedWeatherServer {
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
        if (args.length > 0) {
            try {
                int port = Integer.parseInt(args[0]);
                System.out.println("We are using port: " + port);
                new ThreadedWeatherServer(port, 30, 800).run();
            } catch (IOException | SQLException e) {
                ExceptionLogger.logException(e);
            }
        } else {
            try {
                System.out.println("We are using port: 7789");
                new ThreadedWeatherServer(7789, 30, 800).run();
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}

import helpers.WeatherDatabase;
import interfaces.StorageHandler;
import loggers.ExceptionLogger;
import models.DataQueueBuffer;
import runnables.DataSocketHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;
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
        dataQueueBuffer = new DataQueueBuffer(30, 60);
        storageHandler = new WeatherDatabase();
    }

    @Override
    public void run() {
            try {
                while(true) {
                    pool.execute(new DataSocketHandler(serverSocket.accept(), dataQueueBuffer, storageHandler));
                }
            } catch (IOException e) {
                pool.shutdown();
                ExceptionLogger.logException(e);
            }
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
        try {
            new ThreadedWeatherServer(8080, 1000, 800).run();
        } catch (IOException | SQLException e) {
//                ts.terminate();  // maybe a more subtle solution can be found for the termination of the socket
                ExceptionLogger.logException(e);
        }
    }
}

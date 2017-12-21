import helpers.WeatherDatabase;
import interfaces.StorageHandler;
import loggers.ExceptionLogger;
import models.DataFrameBuffer;
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
    private DataFrameBuffer dataFrameBuffer;
    private StorageHandler storageHandler;

    public ThreadedWeatherServer(int port, int bufferSize, int poolSize) throws IOException, SQLException {
        serverSocket = new ServerSocket(port);
        pool = Executors.newFixedThreadPool(poolSize);
        dataFrameBuffer = new DataFrameBuffer(bufferSize);
        storageHandler = new WeatherDatabase();
    }

    @Override
    public void run() {
            try {
                while(true) {
                    pool.execute(new DataSocketHandler(serverSocket.accept(), dataFrameBuffer, storageHandler));
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
        ThreadedWeatherServer ts = null;
        try {
            ts = new ThreadedWeatherServer(8080, 1000, 800);
            ts.run();
        } catch (IOException e) {
            if (ts != null) {  // maybe a more subtle solution can be found for the termination of the socket
                ts.terminate();
                ExceptionLogger.logException(e);
            }
        } catch (SQLException e) {
            ExceptionLogger.logException(e);
        }
    }
}

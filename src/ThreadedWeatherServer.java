import models.DataFrameBuffer;
import runnables.DataSocketHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Source: https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/ExecutorService.html
 */
public class ThreadedWeatherServer implements Runnable{
    private final ServerSocket serverSocket;
    private final ExecutorService pool;
    private DataFrameBuffer dataFrameBuffer;

    public ThreadedWeatherServer(int port, int bufferSize, int poolSize) throws IOException {
        serverSocket = new ServerSocket(port);
        pool = Executors.newFixedThreadPool(poolSize);
        dataFrameBuffer = new DataFrameBuffer(bufferSize);
    }

    @Override
    public void run() {
            try {
                while(true) {
                    pool.execute(new DataSocketHandler(serverSocket.accept(), this.dataFrameBuffer));
                }
            } catch (IOException ex) {
                pool.shutdown();
            }
    }

    public static void main(String [ ] args)
    {
        try {
            new ThreadedWeatherServer(8080, 100, 800).run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

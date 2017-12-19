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

    public void terminate() {
        try {
            this.serverSocket.close();
        } catch (IOException ex) {
            // What do we catch here
        }
    }

    public static void main(String [ ] args)
    {
        ThreadedWeatherServer ts = null;
        try {
            ts = new ThreadedWeatherServer(8080, 1000, 800);
            ts.run();
            System.out.println("t");
        } catch (IOException e) {
            if (ts != null) {  // maybe a more subtle solution can be found for the termination of the socket
                ts.terminate();
            }
            e.printStackTrace();
        }
    }
}

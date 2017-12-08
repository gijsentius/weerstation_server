package runnables;

import models.DataFrame;

public class DBUpdateHandler implements Runnable {

    public DBUpdateHandler(DataFrame[] dataFrameBuffer) {

    }

    @Override
    public void run() {
        System.out.println("Wow the db update thread is running");
    }
}

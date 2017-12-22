package exceptions;

import interfaces.DataItem;
import models.DataFrame;

import java.util.LinkedList;

public class BufferOverflowPreventException extends Exception {

    private DataFrame[] buffer;
    private LinkedList<DataItem> itemBuffer;

    public BufferOverflowPreventException(String message, DataFrame[] buffer) {
        super(message);
        this.buffer = buffer;
    }

    public BufferOverflowPreventException(String identifier, LinkedList<DataItem> data) {
        super("Buffer size almost exceeded");
        this.itemBuffer = data;
    }

    public DataFrame[] getBuffer() {
        return buffer;
    }
}

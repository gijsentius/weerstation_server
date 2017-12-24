package exceptions;

import interfaces.DataItem;
import models.DataFrame;

import java.util.LinkedList;

public class BufferOverflowPreventException extends Exception {

    private LinkedList<DataItem> buffer;
    private String identifier;

    public BufferOverflowPreventException(LinkedList<DataItem> data) {
        super("Buffer size almost exceeded");
        this.buffer = data;
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public LinkedList<DataItem> getBuffer() {
        return buffer;
    }
}

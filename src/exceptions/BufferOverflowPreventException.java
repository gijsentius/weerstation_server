package exceptions;

import models.DataFrame;

public class BufferOverflowPreventException extends Exception {

    private DataFrame[] buffer;

    public BufferOverflowPreventException(String message, DataFrame[] buffer) {
        super(message);
        this.buffer = buffer;
    }

    public DataFrame[] getBuffer() {
        return buffer;
    }
}

package models;

import exceptions.BufferOverflowPreventException;
import models.DataFrame;

public class DataFrameBuffer {

    private static final int MINIMUM_SIZE = 10;  //the minimum size for the buffer
    private int bufferLength; // semi constant
    // variables that change during run time
    private DataFrame[] buffer;
    private int currentLength;

    public DataFrameBuffer(){
        this.bufferLength = MINIMUM_SIZE;
        this.currentLength = 0;
    }

    public DataFrameBuffer(int length){
        this.bufferLength = length > MINIMUM_SIZE ? length : MINIMUM_SIZE;
        buffer = new DataFrame[this.bufferLength];
        this.currentLength = 0;
    }

    public DataFrame[] getBuffer() {
        return buffer;
    }

    public int getBufferLength() {
        return currentLength;
    }

    public void setBufferLength(int bufferLength) {
        if (bufferLength > MINIMUM_SIZE && bufferLength > currentLength) { // second check is for run time use
            this.bufferLength = bufferLength;
        }
    }

    private void copyBuffer(DataFrame[] oldBuffer, DataFrame[] newBuffer) {
        int index = 0;
        for (DataFrame df : oldBuffer) {
            newBuffer[index] = df;
            index++;
        }
    }

    public synchronized void updateBuffer(DataFrame dataFrame) throws BufferOverflowPreventException{
        if (this.currentLength + 1 < bufferLength) {
            this.buffer[currentLength] = dataFrame;
            this.currentLength++;
        } else {
            DataFrame[] tempBuffer = this.buffer;
            this.buffer = new DataFrame[bufferLength]; // reset the buffer and buffer length
            this.currentLength = 0;
            throw new BufferOverflowPreventException("Buffer size almost exceeded", tempBuffer);
        }
    }
}

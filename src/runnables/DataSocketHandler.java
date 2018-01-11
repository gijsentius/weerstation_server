package runnables;

import exceptions.BufferOverflowPreventException;
import exceptions.InactiveSocketException;
import helpers.DataIntegrityChecker;
import helpers.InputDataParser;
import helpers.XMLReceiver;
import interfaces.DataItem;
import interfaces.StorageHandler;
import loggers.ExceptionLogger;
import models.DataQueueBuffer;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;

/**
 * Some sources used to build this class
 * https://docs.oracle.com/javase/tutorial/networking/sockets/clientServer.html
 * https://docs.oracle.com/javase/8/docs/api/java/net/ServerSocket.html
 * https://stackoverflow.com/questions/4666748/how-to-read-bufferedreader-faster
 */
public class DataSocketHandler implements Runnable {
    private DataQueueBuffer dataQueueBuffer;
//    private ServerSocket serverSocket;
//    private InputDataParser inputDataParser;  // could be static but then synchronised should be used and performance would be lost
    private StorageHandler storageHandler;
    private Socket clientSocket;
    private boolean running;

    public DataSocketHandler(Socket clientSocket, DataQueueBuffer dataQueueBuffer, StorageHandler storageHandler) throws IOException {
        this.dataQueueBuffer = dataQueueBuffer;
        this.storageHandler = storageHandler;
        this.clientSocket = clientSocket;
//        this.inputDataParser = new InputDataParser();
        running = true;
    }

    /**
     * Function to close the socket
     * Is not working and not tested (19-12-17), because information regarding the closing of a thread needs to be found out first
     * @throws IOException
     */
    public void closeConnection() throws IOException {
        clientSocket.close();
//        serverSocket.close();
    }

    @Override
    public void run() {
        while (running) {
            try {
                Document xmlDocument = XMLReceiver.receiveDocument(clientSocket.getInputStream());
                LinkedList<DataItem> dataItems = InputDataParser.parse(xmlDocument);
                DataIntegrityChecker checker = new DataIntegrityChecker(dataQueueBuffer);
                for (DataItem di: dataItems){
                    checker.checkData(di);
                }
                // Hier moet de check data gebeuren
                dataQueueBuffer.update(dataItems);
            } catch (BufferOverflowPreventException e) {
                ExceptionLogger.logException(e);
                new Thread(new DataUpdateHandler(e.getBuffer(), storageHandler)).start();
            }  catch (IOException | TransformerConfigurationException | InactiveSocketException e) {
                ExceptionLogger.logException(e);
                running = false;  // Check for a more subtle solution
            } catch (ParserConfigurationException | SAXException e) {
                ExceptionLogger.logException(e);
            }
        }
    }
}

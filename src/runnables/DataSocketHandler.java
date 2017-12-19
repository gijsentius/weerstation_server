package runnables;

import exceptions.BufferOverflowPreventException;
import exceptions.InactiveSocketException;
import helpers.InputDataParser;
import helpers.XMLReceiver;
import interfaces.DataItem;
import loggers.ExceptionLogger;
import models.DataFrame;
import models.DataFrameBuffer;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import tests.PrettyPrinters;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import java.io.*;
import java.net.Socket;

/**
 * Some sources used to build this class
 * https://docs.oracle.com/javase/tutorial/networking/sockets/clientServer.html
 * https://docs.oracle.com/javase/8/docs/api/java/net/ServerSocket.html
 * https://stackoverflow.com/questions/4666748/how-to-read-bufferedreader-faster
 * TODO: fix the exception logging system
 */
public class DataSocketHandler implements Runnable {
    private DataFrameBuffer dataFrameBuffer;
//    private ServerSocket serverSocket;
    private InputDataParser inputDataParser;  // could be static but then synchronised should be used and performance would be lost
    private Socket clientSocket;
    private boolean running;

    public DataSocketHandler(Socket clientSocket, DataFrameBuffer dataFrameBuffer) throws IOException {
        this.dataFrameBuffer = dataFrameBuffer;
//        this.serverSocket = new ServerSocket(socketNumber);
        this.clientSocket = clientSocket;
        this.inputDataParser = new InputDataParser();
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
                PrettyPrinters.printDocument(xmlDocument, System.out);  //Printer for documents, comment out in production phase
                DataFrame dataFrame = InputDataParser.parse(xmlDocument);
                dataFrameBuffer.updateBuffer(dataFrame);
            } catch (BufferOverflowPreventException e) {
                new Thread(new DataUpdateHandler(e.getBuffer())).start();
            }  catch (IOException | TransformerConfigurationException | InactiveSocketException e) {
                ExceptionLogger.logException(e);
                running = false;  // Check for a more subtle solution
            } catch (ParserConfigurationException | TransformerException | SAXException e) {
                ExceptionLogger.logException(e);
            }
        }
    }
}

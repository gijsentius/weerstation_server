package runnables;

import exceptions.BufferOverflowPreventException;
import exceptions.InactiveSocketException;
import helpers.InputDataParser;
import helpers.XMLReceiver;
import interfaces.DBItem;
import models.DataFrame;
import models.DataFrameBuffer;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;

import java.io.*;
import java.net.Socket;

/**
 * Some sources used to build this class
 * https://docs.oracle.com/javase/tutorial/networking/sockets/clientServer.html
 * https://docs.oracle.com/javase/8/docs/api/java/net/ServerSocket.html
 * https://stackoverflow.com/questions/4666748/how-to-read-bufferedreader-faster
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
     * Is not working and not tested, because information regarding the closing of a thread needs to be found out
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
                DBItem[] dbItems = InputDataParser.parse(xmlDocument);
                DataFrame dataFrame = new DataFrame(dbItems);
                dataFrameBuffer.updateBuffer(dataFrame);
            } catch (BufferOverflowPreventException e) {
                Thread updateDB = new Thread(new DBUpdateHandler(e.getBuffer()));
                updateDB.start();
            } catch (IOException e) {
                running = false;  // Check for a more subtle solution
            } catch (TransformerConfigurationException e) {
                e.printStackTrace();
                running = false;
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
                running = false;
            } catch (SAXException e) {
                e.printStackTrace();
                running = false;
            } catch (InactiveSocketException e) {
                e.printStackTrace();
                running = false;
            }
        }
    }
}

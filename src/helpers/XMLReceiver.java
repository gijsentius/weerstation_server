package helpers;

import exceptions.InactiveSocketException;
import org.w3c.dom.Document;

import java.io.*;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;

/**
 * https://docs.oracle.com/javase/tutorial/jaxp/dom/readingXML.html
 */
public class XMLReceiver {
    public static Document receiveDocument(InputStream  channel) throws ParserConfigurationException,
            TransformerConfigurationException, IOException, SAXException, InactiveSocketException {

        InputStreamReader reader = new InputStreamReader(channel);
        BufferedReader bReader = new BufferedReader(reader);
        StringBuilder sb = new StringBuilder();
        do{
            sb.append(bReader.readLine());
        }
        while(bReader.ready());
        String xml = sb.toString();
        if (xml.equals("null")) {
            throw new InactiveSocketException();
        }

        DocumentBuilderFactory docBuilderFact = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBuilderFact.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xml));
        return docBuilder.parse(is);
    }

    public static String receiveString(InputStream  channel) throws ParserConfigurationException,
            TransformerConfigurationException, IOException, SAXException, InactiveSocketException {

        InputStreamReader reader = new InputStreamReader(channel);
        BufferedReader bReader = new BufferedReader(reader);
        StringBuilder sb = new StringBuilder();
        do{
            sb.append(bReader.readLine());
        }
        while(bReader.ready());
        String xml = sb.toString();
        if (xml.equals("null")) {
            throw new InactiveSocketException();
        }
        return xml;
    }
}

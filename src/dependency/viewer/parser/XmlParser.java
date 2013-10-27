package dependency.viewer.parser;

import java.io.IOException;


import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;

import org.xml.sax.SAXException;

import org.xml.sax.helpers.DefaultHandler;

/**
 * Created with IntelliJ IDEA.
 * User: David
 * Date: 26/10/13
 * Time: 5:42 PM
 */
public class XmlParser extends DefaultHandler {

    private final SAXParserFactory factory;
    private SAXParser parser;
    String tmpValue;
    String type;
    String reference;
    ModuleData currentModule;

    public XmlParser() {
        factory = SAXParserFactory.newInstance();
        try {
            parser = factory.newSAXParser();

        } catch (ParserConfigurationException e) {
            System.out.println("ParserConfig error");
        } catch (SAXException e) {
            System.out.println("SAXException : xml not well formed");
        }

    }

    public ModuleData parseDocument(String path) {
        // parse
        currentModule = new ModuleData();
        try {
            parser.reset();
            parser.parse(path, this);
        } catch (SAXException e) {
            System.out.println("SAXException : xml not well formed");
        } catch (IOException e) {
            System.out.println("IO error");
        }
        return currentModule;
    }

    @Override
    public void startElement(String uri, String localName, String elementName, Attributes attributes) throws SAXException {
        // if current element is book , create new book
        // clear tmpValue on start of element
        if ("includedby".equals(elementName)) {
            String includedby = attributes.getValue("refid");
            currentModule.insertIncludedBy(includedby.substring(0, includedby.length() - 2));
        } else if ("memberdef".equals(elementName)) {
            type = attributes.getValue("kind");
        } else if ("enumvalue".equals(elementName)) {
            type = "enum";
        } else if ("ref".equals(elementName)
                && (attributes.getValue("refid").contains("8c") || attributes.getValue("refid").contains("8h"))
                && !"compound".equals(attributes.getValue("kindref"))) {


            reference = attributes.getValue("refid").split("_")[0];
        }


    }

    @Override
    public void endElement(String uri, String localName, String elementName) throws SAXException {

        if ("name".equals(elementName) && type != null) {
            currentModule.dataObjects.put(tmpValue, type);
            type = null;
        } else if ("compoundname".equals(elementName)) {

            currentModule.setModuleName(tmpValue.substring(0, tmpValue.length() - 2));
        } else if ("includes".equals(elementName)) {
            currentModule.insertInclude(tmpValue);
        }


        if (reference != null) {
            currentModule.addReference(reference, tmpValue);
            reference = null;
        }
    }

    @Override
    public void characters(char[] ac, int i, int j) throws SAXException {
        tmpValue = new String(ac, i, j);
    }


}

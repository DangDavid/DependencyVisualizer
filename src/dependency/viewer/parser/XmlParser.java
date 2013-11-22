package dependency.viewer.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


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

    private SAXParser parser;
    private String xmlContent;
    private String type;
    private String reference;
    private ModuleData currentModule;

    public XmlParser() {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            parser = factory.newSAXParser();

        } catch (ParserConfigurationException e) {
            System.out.println("ParserConfig error");
        } catch (SAXException e) {
            System.out.println("SAXException : xml not well formed");
        }

    }

    public List<ModuleData> parseAll(String folderName) {
        FileSearcher files = new FileSearcher();

        List<String> filePaths = files.getFiles(folderName);


        List<ModuleData> rawData = new ArrayList<ModuleData>();


        for (String path : filePaths) {
            ModuleData parsedData = parseDocument(path);
            rawData.add(parsedData);
        }

        return rawData;
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
            currentModule.putDataObject(xmlContent, type);
            type = null;
        } else if ("compoundname".equals(elementName)) {

            currentModule.setModuleName(xmlContent.substring(0, xmlContent.length() - 2));
        } else if ("includes".equals(elementName)) {
            currentModule.insertInclude(xmlContent);
        }


        if (reference != null) {
            currentModule.addReference(reference, xmlContent);
            reference = null;
        }
    }

    @Override
    public void characters(char[] ac, int i, int j) throws SAXException {
        xmlContent = new String(ac, i, j).trim();
    }


}

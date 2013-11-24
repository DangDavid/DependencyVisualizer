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
 *
 * XmlParser
 * parses a doxygen xml file which represents c code
 *
 * Will only look for the following information
 * file name
 * include list
 * included by list
 * data objects defined in that file
 * external references to data objects from other files
 *
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

    /**
     * General constructor
     */
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

    /**
     * Call this function when you want to parse the xml files
     * @param folderName the folder name that is located in the same place at the project
     * @return a list a moduledata objects. one for each file. source and headers are separate
     */
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

    /**
     * Helper to parser all
     * Parses 1 file
     * @param path  the full path to the file
     * @return  the module data for that file
     */
    private ModuleData parseDocument(String path) {
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

        if ("includedby".equals(elementName)) {   // What files use the current file as an include
            String includedby = attributes.getValue("refid");
            currentModule.insertIncludedBy(includedby.substring(0, includedby.length() - 2));
        } else if ("memberdef".equals(elementName)) {  // a declared data object in the file
            type = attributes.getValue("kind");
        } else if ("enumvalue".equals(elementName)) {  // If the file uses any enums
            type = "enum";
        } else if ("ref".equals(elementName)         // an external reference to another file
                && (attributes.getValue("refid").contains("8c") || attributes.getValue("refid").contains("8h"))
                && !"compound".equals(attributes.getValue("kindref"))) {


            reference = attributes.getValue("refid").split("_")[0];
        }
    }

    @Override
    public void endElement(String uri, String localName, String elementName) throws SAXException {

        if ("name".equals(elementName) && type != null) {      // the module has a data object, store it
            currentModule.putDataObject(xmlContent, type);
            type = null;
        } else if ("compoundname".equals(elementName)) {       // stores the name into module data

            currentModule.setModuleName(xmlContent.substring(0, xmlContent.length() - 2));
        } else if ("includes".equals(elementName)) {                // stores the includes that the file uses
            currentModule.insertInclude(xmlContent);
        }


        if (reference != null) {        // The current file has a reference, store it in the moduledata
            currentModule.addReference(reference, xmlContent);
            reference = null;
        }
    }

    @Override
    public void characters(char[] ac, int i, int j) throws SAXException {
        xmlContent = new String(ac, i, j).trim();
    }


}

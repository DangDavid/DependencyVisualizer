package dependency.viewer;

import dependency.viewer.parser.FileSearcher;
import dependency.viewer.parser.ModuleData;
import dependency.viewer.parser.XmlParser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

/**
 * Created with IntelliJ IDEA.
 * User: David
 * Date: 26/10/13
 * Time: 11:46 AM
 */
public class Main {


    public static void main(String[] args) {
        System.out.println("Start of Main");

        List<ModuleData> rawData = parseStep();

        System.out.println("End of main");
    }

    private static List<ModuleData> parseStep() {
        FileSearcher files = new FileSearcher();

        List<String> filePaths = files.getFiles();

        XmlParser parser = new XmlParser();
        List<ModuleData> rawData = new ArrayList<ModuleData>();

        for (String path : filePaths) {
            ModuleData parsedData = parser.parseDocument(path);
            System.out.println("Finished parsing ");
            parsedData.summerize();
            parsedData.print();
            rawData.add(parsedData);
        }

        System.out.println("Number of files parsed : " + rawData.size());

        return rawData;
    }


}

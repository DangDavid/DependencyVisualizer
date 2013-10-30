package dependency.viewer;

import dependency.viewer.parser.FileSearcher;
import dependency.viewer.parser.ModuleData;
import dependency.viewer.parser.XmlParser;
import dependency.viewer.visualizer.*;

import java.io.File;
import java.util.*;

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

        //drawGraph();

        System.out.println("End of main");
    }

    private static void drawGraph() {
        VisualizerParser vis = new VisualizerParser();
        vis.drawGraph();
    }

    private static List<ModuleData> parseStep() {
        FileSearcher files = new FileSearcher();

        List<String> filePaths = files.getFiles();

        XmlParser parser = new XmlParser();
        List<ModuleData> rawData = new ArrayList<ModuleData>();

        Set<String> fullTypeSet = new HashSet<String>();

        for (String path : filePaths) {
            ModuleData parsedData = parser.parseDocument(path);
            System.out.println("Finished parsing ");
            fullTypeSet.addAll(parsedData.getDataObjectTypes());
            parsedData.summerize();
            rawData.add(parsedData);
        }

        System.out.println("Number of files parsed : " + rawData.size());

        // [define, enum, variable, typedef, function]
        System.out.println("\n\nThe full list of type \n\n" + fullTypeSet);
        return rawData;
    }


}

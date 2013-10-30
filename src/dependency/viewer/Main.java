package dependency.viewer;

import dependency.viewer.mapper.DependencyData;
import dependency.viewer.mapper.DependencyGraph;
import dependency.viewer.mapper.SortDependencies;
import dependency.viewer.parser.FileSearcher;
import dependency.viewer.parser.ModuleData;
import dependency.viewer.parser.XmlParser;
import dependency.viewer.visualizer.*;

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
        SortDependencies sorter = new SortDependencies();
        List<DependencyGraph> matrices = sorter.mapDependency(rawData);


        Integer[][] m0 = matrices.get(0).getMatrix();
        Integer[][] m1 = matrices.get(1).getMatrix();


        for (Integer[] row : m0) {
            for (Integer i : row) {
                System.out.print(" " + i);
            }
            System.out.println(" ");
        }


        System.out.println(" ");
        System.out.println(" ");

        for (Integer[] row : m1) {
            for (Integer i : row) {
                System.out.print(" " + i);
            }
            System.out.println(" ");
        }
        String[] files = matrices.get(0).getFileNameDirectory();
        String[] files1 = matrices.get(1).getFileNameDirectory();
        for (int i = 0; i < files.length; i++) {
            System.out.println(files[i] + "  " + files1[i]);
        }


    }

    private static void drawGraph() {
        VisualizerParser vis = new VisualizerParser();
        vis.drawGraph();
    }


    public static List<ModuleData> parseStep() {
        FileSearcher files = new FileSearcher();

        List<String> filePaths = files.getFiles();

        XmlParser parser = new XmlParser();
        List<ModuleData> rawData = new ArrayList<ModuleData>();

        Set<String> fullTypeSet = new HashSet<String>();

        for (String path : filePaths) {
            ModuleData parsedData = parser.parseDocument(path);
            // System.out.println("Finished parsing ");
            fullTypeSet.addAll(parsedData.getDataObjectTypes());
            // parsedData.summerize();
            // parsedData.print();
            rawData.add(parsedData);
        }

        // System.out.println("Number of files parsed : " + rawData.size());

        // [define, enum, variable, typedef, function]
        // System.out.println("\n\nThe full list of type \n\n" + fullTypeSet);
        return rawData;
    }


}

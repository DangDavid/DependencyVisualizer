package dependency.viewer;

import dependency.viewer.mapper.DependencyData;
import dependency.viewer.mapper.DependencyEdge;
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
    private static final String INITIAL_DIR = "xml_2"; // the folder which had the initial source code in xml for comparison
    private static final String FINAL_DIR = "xml_2_10";    // the folder which has the xml for the source code to be compared too

    public static void main(String[] args) {
        System.out.println("Start of Main");

        // Parse The Two Sets Of Data
        XmlParser parser = new XmlParser();
        List<ModuleData> rawInitialData = parser.parseAll(INITIAL_DIR);
        List<ModuleData> rawFinalData = parser.parseAll(FINAL_DIR);


        // Map Each Set Of Data
        SortDependencies sorter = new SortDependencies();
        List<DependencyGraph> matricesI = sorter.mapDependency(rawInitialData);

        List<DependencyGraph> matricesF = sorter.mapDependency(rawFinalData);

        // Graph Each Set Of Data
        //DependencyGraph mat = matrices.get(0);
        //matrixToDigraph(mat);
        drawGraph(matricesI, matricesF);


    }


    private static void drawGraph(List<DependencyGraph> initG, List<DependencyGraph> finalG) {
        VisualizerParser vis = new VisualizerParser(initG, finalG);
        //vis.drawGraph();
        vis.matrixToDigraph();
    }


}

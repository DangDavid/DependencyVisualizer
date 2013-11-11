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
        List<DependencyGraph> matrices = sorter.mapDependency(rawFinalData);


        Integer[][] m0 = matrices.get(0).getMatrix();
        Integer[][] m1 = matrices.get(1).getMatrix();

        List<DependencyData> list = sorter.sortDataDependency(rawFinalData);

        DependencyData data = list.get(1);    //get behavioural


        // Graph Each Set Of Data
        DependencyGraph mat = matrices.get(0);
        //matrixToDigraph(mat);
        //drawGraph(m1);


    }


    private static void drawGraph(Integer[][] matrix) {
        VisualizerParser vis = new VisualizerParser(matrix);
        vis.drawGraph();
    }


    private static void matrixToDigraph(DependencyGraph graph) {


        Integer[][] matrix = graph.getMatrix();
        String[] names = graph.getFileNameDirectory();

        String diGraph = "graph G {\n";
        for (Integer j = 0; j < matrix.length; j++) {
            for (Integer i = 0; i < j; i++) {
                if (matrix[i][j] > 0) {
                    String dependency = names[i];
                    dependency += " -- ";
                    dependency += names[j];

                    dependency += ";\n";
                    diGraph += dependency;
                }
            }
        }
        diGraph += "\n}";
        System.out.println(diGraph);
    }

}

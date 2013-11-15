package dependency.viewer.visualizer;

import dependency.viewer.mapper.DependencyGraph;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: taehyunkang
 * Date: 2013-10-27
 * Time: 9:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class VisualizerParser {
    DependencyGraph dependencyGraph;

    public VisualizerParser(DependencyGraph graph) {
        dependencyGraph = graph;
    }


    /**
     * generate a giant String(dot language of the graph)
     * @return String
     */
    private String matrixToDigraph() {
        Map<String, List<String>> cluster = dependencyGraph.getClusters();
        String[] fileNameDirectory = dependencyGraph.getFileNameDirectory();
        Map<Integer, List<Integer>> intCluster = convertCluster(cluster, fileNameDirectory);
        System.out.println(intCluster);

        String diGraph = "graph G {\n";
        String dependency = "";
        List<Integer> keys = new ArrayList<Integer>();
        for (String file : fileNameDirectory) {
            Integer key = dependencyGraph.getModuleIndex(file);
            //block for big cluster
            if (key != 40){
            if (intCluster.containsKey(key)) {
                keys.add(key);
                dependency = "subgraph cluster";
                dependency += key;
                dependency += " { \n";
                for (Integer fileNum : intCluster.get(key)) {
                    dependency += key;
                    dependency += " -- ";
                    dependency += fileNum;
                    dependency += ";\n";
                }
                dependency += "}\n";
                diGraph += dependency;
            }
            }
        }
        /*
        //TODO-KEVIN: make graph work for bigger cluster
        for (Integer key : keys) {
            diGraph += key;
            diGraph += " -- cluster";
            diGraph += "40";
            diGraph += "\n";
        }
        */
        diGraph += "\n}";
        return diGraph;
    }

    private Map<Integer, List<Integer>> convertCluster(Map<String, List<String>> cluster, String[] fileNameDirectory) {
        System.out.println(fileNameDirectory);
        Map<Integer, List<Integer>> indexCluster = new HashMap<Integer, List<Integer>>();
        for (String file: fileNameDirectory) {
            if (cluster.containsKey(file)) {
                Integer moduleIndex = dependencyGraph.getModuleIndex(file);
                List<String> fileList = cluster.get(file);
                List<Integer> intList = new ArrayList<Integer>();
                for (String file1 : fileList) {
                    intList.add(dependencyGraph.getModuleIndex(file1));
                }
                indexCluster.put(moduleIndex, intList);
            }
        }
        return indexCluster;
    }

    private Double scaleDependencySize(Double i) {
        return 0.75 + (i / 100);
    }

    public void drawGraph()
    {
        String diGraph = matrixToDigraph();
        GraphViz gv = new GraphViz();
        gv.addln(diGraph);
        System.out.println(gv.getDotSource());

        String type = "gif";
        File out = new File("/tmp/out." + type);
        gv.writeGraphToFile( gv.getGraph( gv.getDotSource(), type ), out );
    }

    public Boolean isDependency(Integer dependency) {
        return (dependency == 0)? false : true;
    }
}

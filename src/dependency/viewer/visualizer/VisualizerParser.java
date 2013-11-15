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
        List<String> clusterList = new ArrayList<String>();
        System.out.println(cluster);

        String diGraph = "graph G {\n" +
                "sep=\"+25,25\";\n" +
                "nodesep=0.6;\n" +
                "edge[weight=0.8];\n" +
                "overlap = false;\n" +
                "splines=true;\n";
        String dependency = "";
        String nodes = "";
        String node = "";
        List<String> keys = new ArrayList<String>();
        for (String file : fileNameDirectory) {
            String key = file;
            //block for big cluster
            if (cluster.containsKey(key)) {
                keys.add(key);
                dependency += "subgraph cluster";
                dependency += key;
                dependency += " { \n";
                for (String f : cluster.get(key)) {
                    clusterList.add(f);
                    dependency += f;
                    dependency += ";\n";
                }
                dependency += "}\n";
            }
        }
        System.out.println("files:");
        System.out.println(fileNameDirectory.length);
        System.out.println("clusterList:");
        System.out.println(clusterList.size());
        System.out.println("keys:");
        System.out.println(keys.size());
        for (String file : fileNameDirectory) {
            Integer[] fileIndex = dependencyGraph.getMatrix()[dependencyGraph.getModuleIndex(file)];
            node += file;
            node += "\n";
            for (int i = 0; i < fileIndex.length; i++){
                if (isDependency(fileIndex[i])) {
                nodes += fileNameDirectory[i];
                nodes += " -- ";
                nodes += file;
                nodes += ";\n";
                }
            }
        }
        //diGraph += nodes;
        diGraph += dependency;
        diGraph += "\n}";
        return diGraph;
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

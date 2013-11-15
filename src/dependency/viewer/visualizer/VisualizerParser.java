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
    List<DependencyGraph> dependencyGraph;

    public VisualizerParser(List<DependencyGraph> graph) {
        dependencyGraph = graph;
    }


    public String matrixToDigraph() {
        DependencyGraph finalBehavioural = dependencyGraph.get(1);
        Map<String, List<String>> cluster = finalBehavioural.getClusters();
        String[] fileNameDirectory = finalBehavioural.getFileNameDirectory();
        List<String> clusterList = new ArrayList<String>();
        System.out.println(cluster);

        String diGraph = "graph G {\n" +
                "sep=\"+25,25\";\n" +
                "nodesep=0.6;\n" +
                "edge[weight=0.8];\n" +
                "overlap = false;\n" +
                "splines=true;\n" +
                "node [shape = circle ,sides = 4,distortion = 0.0,orientation = 0.0,skew = 0.0 ];\n";

        String dependency = "";
        String nodes = "";
        String node = "";
        List<String>listOfcolor = new ArrayList<String>();
        listOfcolor.add("red");
        listOfcolor.add("yellow");
        listOfcolor.add("blue");
        listOfcolor.add("purple");
        listOfcolor.add("orange");
        listOfcolor.add("green");
        listOfcolor.add("cyan");
        listOfcolor.add("tan");
        listOfcolor.add("brown");
        listOfcolor.add("pink");
        listOfcolor.add("olive");
        listOfcolor.add("grey");
        listOfcolor.add("gold");
        listOfcolor.add("coral");
        listOfcolor.add("labender");
        listOfcolor.add("fuchasia");
        listOfcolor.add("forestgreen");
        listOfcolor.add("blueviolet");
        listOfcolor.add("deepskyblue");
        listOfcolor.add("salmon");
        
     int index = 0;
       for(String key : cluster.keySet()){
    	
    	    if (cluster.containsKey(key) && cluster.get(key).size() > 1) {
    	    	  String color = listOfcolor.get(index);
    	   for(String f: cluster.get(key)){
    		   dependency += f;
    		   dependency += "[ color="+ color +", style = filled];\n";

    	   }    	    
    	   index++;

    	   
    	    }
       }
       
        
        
        List<String> keys = new ArrayList<String>();
        for (String file : fileNameDirectory) {
            String key = file;
            //block for big cluster
            if (cluster.containsKey(key) && cluster.get(key).size() > 1) {
                keys.add(key);
                dependency += "subgraph cluster_";
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


        DependencyGraph finalData = dependencyGraph.get(0);
        fileNameDirectory = finalData.getFileNameDirectory();

        /*
        for (String file : fileNameDirectory) {
            Integer[] fileIndex = finalData.getMatrix()[finalData.getModuleIndex(file)];
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
        }      */

        Integer[][] matrix = finalData.getMatrix();
        for (Integer j = 0; j < matrix.length; j++) {
            for (Integer i = 0; i < j; i++) {
                if (isDependency(matrix[i][j])) {
                    nodes += fileNameDirectory[i];
                    nodes += " -- ";
                    nodes += fileNameDirectory[j];
                    nodes += ";\n";
                }
            }
        }

        diGraph += dependency;
        diGraph += nodes;
        diGraph += "\n}";
        return diGraph;
    }

    private Double scaleDependencySize(Double i) {
        return 0.75 + (i / 100);
    }

    public void drawGraph() {
        String diGraph = matrixToDigraph();
        GraphViz gv = new GraphViz();
        gv.addln(diGraph);
        System.out.println(gv.getDotSource());

        String type = "gif";
        File out = new File("C:/temp/out." + type);
        gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), type), out);
    }

    public Boolean isDependency(Integer dependency) {
        return (dependency == 0) ? false : true;
    }
}

package dependency.viewer.visualizer;

import dependency.viewer.mapper.DependencyEdge;

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


    /**
     * generate a giant String(dot language of the graph)
     * @return String
     */
    public String matrixToDigraph() {
//    	// get the list of new modules, keep its structure as map for now(only need module name for now)
//    	Map<String, List<DependencyEdge>> difference = dependencyGraph.getDependencyDataDifference();
//    	String shape = "star";
    	
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
                "splines=true;\n";
        String dependency = "";
        String nodes = "";
        String node = "";
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
                    
//                    /* new node will be given a different shape when being declared in dot language*/
//                    if(isNewNode(f, difference)){
//                    	dependency += " [shape=";
//                    	dependency += shape;
//                    	dependency += "]";
//                    }	
                    
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

    /**
     * calculate if a given string f exists in difference:
     * 		if difference is not null and key exists in difference, then return true
     * 			,else(difference is null or the key does not exist) return false
     * @param f - String
     * @param difference - Map<String, List<DependencyEdge>>
     * @return boolean
     */
    private boolean isNewNode(String f, Map<String, List<DependencyEdge>> difference) {
    	    
		return ( (difference != null) && (difference.containsKey(f)) );
	}


	private Double scaleDependencySize(Double i) {
        return 0.75 + (i / 100);
    }

    public void drawGraph(){
        String diGraph = matrixToDigraph();
        GraphViz gv = new GraphViz();
        gv.addln(diGraph);
        System.out.println(gv.getDotSource());

        String type = "gif";
        File out = new File("/tmp/out." + type);
        gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), type), out);
    }

    public Boolean isDependency(Integer dependency) {
        return (dependency == 0)? false : true;
    }
}

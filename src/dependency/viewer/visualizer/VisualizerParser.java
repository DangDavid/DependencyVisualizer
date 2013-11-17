package dependency.viewer.visualizer;

import dependency.viewer.mapper.DependencyGraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
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
    List<DependencyGraph> initalGraph;

    List<DependencyGraph> finalGraph;
    private ArrayList<String> listOfcolor;

    private String graphHeader = "graph G {\n" +
            "sep=\"+25,25\";\n" +
            "nodesep=0.6;\n" +
            "edge[weight=0.8];\n" +
            "overlap = false;\n" +
            "splines=true;\n" +
            "node [shape = circle,width = 1.4,height= 1.4,fixedsize = true,sides = 4,distortion = 0.0,orientation = 0.0,skew = 0.0 , style = filled];\n";


    public VisualizerParser(List<DependencyGraph> initG, List<DependencyGraph> finalG) {
        initalGraph = initG;
        finalGraph = finalG;

        listOfcolor = new ArrayList<String>();
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
    }


    public void matrixToDigraph() {
        DependencyGraph initBehaviour = initalGraph.get(1);
        Map<String, List<String>> cluster = initBehaviour.getClusters();


        String colorNodes = assignColor(cluster);

        String initGraphString = makeGraph(initalGraph.get(0), initalGraph.get(1), colorNodes);
        String finalGraphString = makeGraph(finalGraph.get(0), finalGraph.get(1), colorNodes, initalGraph.get(0).getFileNameDirectory());


        fileWrite(initGraphString, finalGraphString);


    }

    private String makeGraph(DependencyGraph dataGraph, DependencyGraph behaviourGraph,
                             String colorNodes) {
        Map<String, List<String>> cluster = behaviourGraph.getClusters();
        String[] fileNameDirectory = behaviourGraph.getFileNameDirectory();
        String diGraph = graphHeader;
        String dependency = "";
        String nodes = "";

        dependency += colorNodes;


        List<String> keys = new ArrayList<String>();
        for (String file : fileNameDirectory) {
            String key = file;
            //block for big cluster
            if (cluster.containsKey(key) && cluster.get(key).size() > 1) {
                keys.add(key);
                dependency += "subgraph cluster_";
                dependency += key;
                dependency += " { \n";
                dependency += "penwidth= 10\n";
                for (String f : cluster.get(key)) {
                    dependency += f;
                    dependency += ";\n";
                }
                dependency += "color = indigo\n";
                dependency += "}\n";
            }
        }


        fileNameDirectory = dataGraph.getFileNameDirectory();


        Integer[][] matrix = dataGraph.getMatrix();
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

    private String makeGraph(DependencyGraph dataGraph, DependencyGraph behaviourGraph,
                             String colorNodes, String[] detectNewFiles) {
        Map<String, List<String>> cluster = behaviourGraph.getClusters();
        String[] fileNameDirectory = behaviourGraph.getFileNameDirectory();
        String diGraph = graphHeader;
        String dependency = "";
        String nodes = "";

        dependency += colorNodes;


        List<String> existingFiles = new ArrayList<String>(Arrays.asList(detectNewFiles));
        for (String file : fileNameDirectory) {
            if (!existingFiles.contains(file)) {
                dependency += file + "[shape = invtriangle, color= hotpink1 ];\n";
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
                dependency += "penwidth= 10\n";
                for (String f : cluster.get(key)) {

                    dependency += f;
                    dependency += ";\n";
                }
                dependency += "color = indigo\n";
                dependency += "}\n";
            }
        }


        fileNameDirectory = dataGraph.getFileNameDirectory();


        Integer[][] matrix = dataGraph.getMatrix();
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

    private String assignColor(Map<String, List<String>> cluster) {
        String colorNodes = "";
        int index = 0;
        for (String key : cluster.keySet()) {

            if (cluster.containsKey(key) && cluster.get(key).size() > 1) {
                String color = listOfcolor.get(index);
                for (String f : cluster.get(key)) {

                    colorNodes += f + "[ color=" + color + "];\n";

                }
                index++;


            }
        }
        return colorNodes;
    }

    private Double scaleDependencySize(Double i) {
        return 0.75 + (i / 100);
    }

    public void drawGraph() {
        String diGraph = ""; //matrixToDigraph();
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

    private void fileWrite(String initG, String finalG) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("init.txt", "UTF-8");
            writer.println(initG);

            writer.close();


            writer = new PrintWriter("final.txt", "UTF-8");
            writer.println(finalG);

            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


    }
}

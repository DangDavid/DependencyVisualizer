package dependency.viewer.mapper;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: David
 * Date: 26/10/13
 * Time: 1:29 PM
 * <p/>
 * This class should only be used to create a visualization on. To create and set up the data, use
 * the DependencyData class to add dependencies.
 */
public class DependencyGraph {
    DependencyType type;                       // determines what type of graph this object is
    Integer[][] matrix;                        // holds the value of the dependencies between module
    String[] fileNameDirectory;                // holds the file name, used to get the index for the matrix
    Map<String, List<String>> clusters;    // Key is the cluster which the modules belongs to, and value is the list of modules that belong there

    public DependencyGraph(DependencyData model) {
        this.type = model.getType();
        clusters = new HashMap<String, List<String>>();
        initGraph(model);
    }

    private void initGraph(DependencyData model) {
        int size = model.getSize();
        matrix = new Integer[size][size];

        for (int i = 0; i < size; i++) {

            for (int j = 0; j < size; j++) {
                matrix[i][j] = 0;
            }

        }

        fileNameDirectory = new String[size];

        Map<String, List<DependencyEdge>> map = model.getNodeMap();
        Set<String> keys = map.keySet();
        int index = 0;
        for (String moduleName : keys) {
            fileNameDirectory[index] = moduleName;
            index++;

        }

        for (String moduleName : keys) {
            List<DependencyEdge> edges = map.get(moduleName);
            int parentIndex = getModuleIndex(moduleName);
            for (DependencyEdge edge : edges) {
                int childIndex = getModuleIndex(edge.getChildNode());
                if (childIndex == -1 || parentIndex == -1) {
                    System.out.println();
                }
                matrix[childIndex][parentIndex] += edge.getNumberOfDependencies();
                matrix[parentIndex][childIndex] += edge.getNumberOfDependencies();
            }

        }


        Map<String, List<DependencyEdge>> dataNodes = model.getNodeMap();
        for (String key : dataNodes.keySet()) {
            String clusterName = null;
            List<DependencyEdge> edges = dataNodes.get(key);

            DependencyEdge maxEdge = null;

            for (DependencyEdge edge : edges) {

                if (maxEdge == null || edge.getNumberOfDependencies() > maxEdge.getNumberOfDependencies()) {

                    maxEdge = edge;

                }

            }

            if (maxEdge != null) {
                clusterName = maxEdge.getChildNode();

            } else {
                clusterName = key;
            }

            if (clusters.containsKey(clusterName)) {
                clusters.get(clusterName).add(key);
            } else {
                List<String> value = new ArrayList<String>();
                value.add(key);
                clusters.put(clusterName, value);
            }

        }


    }

    public int getModuleIndex(String name) {


        for (int index = 0; index < fileNameDirectory.length; index++) {
            if (name.equals(fileNameDirectory[index])) {

                return index;
            }

        }
        return -1;

    }


    public Map<String, List<String>> getClusters() {
        return clusters;
    }
    public String[] getFileNameDirectory() {
        return fileNameDirectory;
    }

    public Integer[][] getMatrix() {
        return matrix;
    }
}

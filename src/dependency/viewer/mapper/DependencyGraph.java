package dependency.viewer.mapper;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: David
 * Date: 26/10/13
 * Time: 1:29 PM
 * <p/>
 * This class makes abstract the structure of a DependencyData by making matrix out of it, and
 * it also groups modules to form clusters by running certain algorithms.
 */
public class DependencyGraph {
    // determines what type of graph this object is
    DependencyType type;
    // holds the value of the dependencies between module
    Integer[][] matrix;
    // holds the file name, used to get the index for the matrix
    String[] fileNameDirectory;
    // Key is the cluster which the modules belongs to, and value is the list of modules that belong there
    Map<String, List<String>> clusters;


    /**
     * Default constructor
     *
     * @param model - DependencyData
     */
    public DependencyGraph(DependencyData model) {
        this.type = model.getType();
        clusters = new HashMap<String, List<String>>();
        initGraph(model);
    }

    /**
     * Initialize the current DependencyGraph instance, by generating values for
     * matrix, fileNameDirectory and clusters
     *
     * @param model - DependencyData
     */
    private void initGraph(DependencyData model) {
        int size = model.getSize();
        matrix = new Integer[size][size];

		/* initialize the matrix by filling it with zeros  */
        for (int i = 0; i < size; i++) {

            for (int j = 0; j < size; j++) {
                matrix[i][j] = 0;
            }

        }

        fileNameDirectory = new String[size];


		/* process the matrix to fill in the number of dependencies for each pair of modules  */
        Map<String, List<DependencyEdge>> map = model.getNodeMap();
        // keys are all module names in the current "model"(DependencyData)
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


		/* generate clusters by grouping related modules */
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

    /**
     * Get the index of the module: name
     * index applies to the above 2-D array
     */
    public int getModuleIndex(String name) {


        for (int index = 0; index < fileNameDirectory.length; index++) {
            if (name.equals(fileNameDirectory[index])) {

                return index;
            }

        }
        return -1;

    }

    /**
     * A getter method for the variable clusters
     *
     * @return
     */
    public Map<String, List<String>> getClusters() {
        return clusters;
    }

    /**
     * A getter method for the variable fileNameDirectory
     *
     * @return
     */
    public String[] getFileNameDirectory() {
        return fileNameDirectory;
    }

    /**
     * A getter method for the variable matrix
     *
     * @return
     */
    public Integer[][] getMatrix() {
        return matrix;
    }


}

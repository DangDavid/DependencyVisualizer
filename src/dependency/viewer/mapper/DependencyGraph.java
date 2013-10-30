package dependency.viewer.mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

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

    public DependencyGraph(DependencyData model) {
        this.type = model.getType();
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


    }

    private int getModuleIndex(String name) {


        for (int index = 0; index < fileNameDirectory.length; index++) {
            if (name.equals(fileNameDirectory[index])) {

                return index;
            }

        }
        return -1;

    }


    public String[] getFileNameDirectory() {
        return fileNameDirectory;
    }

    public Integer[][] getMatrix() {
        return matrix;
    }
}

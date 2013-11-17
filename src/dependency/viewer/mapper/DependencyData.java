package dependency.viewer.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: David
 * Date: 26/10/13
 * Time: 1:34 PM
 * <p/>
 * This class is mainly for creating the data for the graph. This class will contain the raw information
 * sorted to the correct types, every node and their edges.
 */
public class DependencyData {
    private Map<String, List<DependencyEdge>> nodeMap;
    DependencyType type;

    /**
     * Default constructor
     * @param object
     * @param data
     */
    public DependencyData(Map<String, List<DependencyEdge>> object, DependencyType data) {
        this.nodeMap = object;
        this.type = data;
    }

    /**
     * A getter method for variable type
     * @return
     */
    public DependencyType getType() {
        return type;
    }

    /**
     * A getter method for the size of nodeMap
     * @return
     */
    public int getSize() {
        return nodeMap.size();
    }

    /**
     * A setter method to set the type of this DependencyData
     * @param type
     */
    public void setType(DependencyType type) {
        this.type = type;
    }

    /**
     * Given a parent and a child, add an edge for the parent node in nodeMap 
     * @param key
     * @param child
     */
    public void addDependency(String key, String child) {
        if (key.equals(child)) {
            return;
        }

        List<DependencyEdge> edges;

        if (nodeMap.containsKey(key)) {
            edges = nodeMap.get(key);
        } else {
            edges = new ArrayList<DependencyEdge>();
            nodeMap.put(key, edges);
        }

        for (DependencyEdge e : edges) {
            if (e.getChildNode().equals(child)) {
                e.increNumberOfDependencies();
                return;
            }
        }

        edges.add(new DependencyEdge(key, child, 1));


    }

    /**
     * A getter method for the variable nodeMap
     * @return
     */
    public Map<String, List<DependencyEdge>> getNodeMap() {
        return nodeMap;

    }

    /**
     * Initialize the DependencyData instance by adding a new list of DependencyEdge's
     * in the nodeMap
     * @param key
     */
    public void initDependency(String key) {


        if (nodeMap.containsKey(key)) {
            return;
        } else {
            nodeMap.put(key, new ArrayList<DependencyEdge>());
        }


    }
}

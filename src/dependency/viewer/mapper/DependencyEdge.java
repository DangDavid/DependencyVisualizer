package dependency.viewer.mapper;

/**
 * Created with IntelliJ IDEA.
 * User: David
 * Date: 26/10/13
 * Time: 2:28 PM
 * <p/>
 * This class defines a dependency relation(i.e. edge) between two nodes: parent and child,
 * and it also stores information on the level of dependency
 */
public class DependencyEdge {
    String parentNode;
    String childNode;//(
    int numberOfDependencies;

    /**
     * Default constructor
     * @param parent
     * @param child
     * @param initialDependency
     */
    public DependencyEdge(String parent, String child, int initialDependency) {
        parentNode = parent;
        childNode = child;
        numberOfDependencies = initialDependency;
    }

    /**
     * A getter method to get the name of the child node
     * @return
     */
    public String getChildNode() {
        return childNode;
    }

    /**
     * A getter method to get the name of the parent node
     * @return
     */
    public String getParentNode() {
        return parentNode;
    }

    /**
     * A getter method to get the level of dependency between the parent and the child
     * @return
     */
    public int getNumberOfDependencies() {
        return numberOfDependencies;
    }

    /**
     * Increaments the level of dependency
     */
    public void increNumberOfDependencies() {
        this.numberOfDependencies++;
    }

}


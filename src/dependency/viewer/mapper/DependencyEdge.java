package dependency.viewer.mapper;

/**
 * Created with IntelliJ IDEA.
 * User: David
 * Date: 26/10/13
 * Time: 2:28 PM
 * <p/>
 * Holds information about the edge that the parent node has
 */
public class DependencyEdge {
    String parentNode;
    String childNode;//(
    int numberOfDependencies;

    public DependencyEdge(String parent, String child, int initialDependency) {
        parentNode = parent;
        childNode = child;
        numberOfDependencies = initialDependency;
    }


    public String getChildNode() {
        return childNode;
    }

    public String getParentNode() {
        return parentNode;
    }

    public int getNumberOfDependencies() {
        return numberOfDependencies;
    }

    public void increNumberOfDependencies() {
        this.numberOfDependencies++;
    }

}


package dependency.viewer.mapper;

/**
 * Created with IntelliJ IDEA.
 * User: David
 * Date: 26/10/13
 * Time: 1:34 PM
 *
 * This class is mainly for creating the data for the graph. This class will contain the raw information
 * sorted to the correct types, every node and their edges.
 */
public class DependencyData {
    private int size;
    DependencyType type;

    public DependencyType getType() {
        return type;
    }

    public int getSize() {
        return size;
    }
}

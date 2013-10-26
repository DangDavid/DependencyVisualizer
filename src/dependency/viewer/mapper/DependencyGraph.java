package dependency.viewer.mapper;

/**
 * Created with IntelliJ IDEA.
 * User: David
 * Date: 26/10/13
 * Time: 1:29 PM
 *
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
        fileNameDirectory = new String[size];
        //TODO: change the model into a matrix graph so it is easier to work with
    }


    public String[] getFileNameDirectory() {
        return fileNameDirectory;
    }

    public Integer[][] getMatrix(){
        return matrix;
    }
}

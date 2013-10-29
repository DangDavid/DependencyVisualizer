package dependency.viewer.visualizer;

import dependency.viewer.mapper.DependencyGraph;

/**
 * Created with IntelliJ IDEA.
 * User: taehyunkang
 * Date: 2013-10-27
 * Time: 9:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class VisualizerParser {
    private DependencyGraph dependencyGraph;
    private String diGraph;

    public VisualizerParser(DependencyGraph dependencyGraph) {
        this.dependencyGraph = dependencyGraph;
        this.diGraph = matrixToDigraph(dependencyGraph.getMatrix());
    }

    public String matrixToDigraph(Integer[][] matrix) {
        String diGraph = "digraph G {\n";
        for (Integer j = 0; j < matrix.length; j++) {
            for (Integer i = 0; i < matrix[j].length; i++) {
                if (isDependency(matrix[i][j])) {
                    String dependency = j.toString().concat(" -> ").concat(i.toString()).concat(";");
                    diGraph.concat(dependency);
                }
            }
        }
        diGraph.concat("\n}");
        return diGraph;
    }

    public Boolean isDependency(Integer dependency) {
        return true;
    }
}

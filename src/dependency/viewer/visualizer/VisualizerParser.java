package dependency.viewer.visualizer;

import dependency.viewer.mapper.DependencyGraph;
import java.io.File;

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
    private Integer[][] matrix;

    /*public VisualizerParser(DependencyGraph dependencyGraph) {
        this.dependencyGraph = dependencyGraph;
        this.diGraph = matrixToDigraph(dependencyGraph.getMatrix());
    }*/

    public VisualizerParser() {
        this.matrix = new Integer[2][2];
        this.matrix[0][0] = 1;
        this.matrix[0][1] = 1;
        this.matrix[1][0] = 1;
        this.matrix[1][1] = 1;
    }


    private String matrixToDigraph(Integer[][] matrix) {
        String diGraph = "digraph G {\n";
        for (Integer j = 0; j < matrix.length; j++) {
            for (Integer i = 0; i < matrix[j].length; i++) {
                if (isDependency(matrix[i][j])) {
                    String dependency = j.toString();
                    dependency += " -> ";
                    dependency += i.toString();
                    dependency += ";\n";
                    diGraph += dependency;
                }
            }
        }
        diGraph += "\n}";
        return diGraph;
    }

    public void drawGraph()
    {
        String diGraph = matrixToDigraph(this.matrix);
        GraphViz gv = new GraphViz();
        gv.addln(diGraph);
        System.out.println(gv.getDotSource());

        String type = "gif";
        File out = new File("/tmp/out." + type);
        gv.writeGraphToFile( gv.getGraph( gv.getDotSource(), type ), out );
    }
    // TODO: Implement
    public Boolean isDependency(Integer dependency) {
        return true;
    }
}

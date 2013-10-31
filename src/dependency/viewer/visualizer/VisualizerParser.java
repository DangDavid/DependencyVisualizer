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
    private Integer[][] matrix;

    public VisualizerParser(Integer[][] matrix) {
        this.matrix = matrix;
    }



    private String matrixToDigraph(Integer[][] matrix) {
        String diGraph = "digraph G {\n";
        for (Integer j = 0; j < matrix.length; j++) {
            for (Integer i = 0; i < matrix[j].length; i++) {
                if (isDependency(matrix[i][j])) {
                    String dependency = j.toString();
                    dependency += " -- ";
                    dependency += i.toString();
                    dependency += "[label=\" \",penwidth=";
                    dependency += scaleDependencySize(matrix[i][j].doubleValue()).toString();
                    dependency += "];\n";
                    diGraph += dependency;
                }
            }
        }
        diGraph += "\n}";
        return diGraph;
    }

    private Double scaleDependencySize(Double i) {
        return 0.75 + (i / 100);
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
        return (dependency == 0)? false : true;
    }
}

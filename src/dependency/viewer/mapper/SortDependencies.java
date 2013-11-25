package dependency.viewer.mapper;


import dependency.viewer.parser.ModuleData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SortDependencies {

    private DependencyData dataDependency;
    private DependencyData behavioralDependency;

    List<ModuleData> rawInitialData;
    List<ModuleData> rawFinalData;

    /**
     * Constructor
     * @param rawInitialData
     * @param rawFinalData
     */
    public SortDependencies(List<ModuleData> rawInitialData, List<ModuleData> rawFinalData) {
        this.rawInitialData = rawInitialData;
        this.rawFinalData = rawFinalData;


    }


/**
 * Initialization
 * @param rawData
 */
    public void sortDataDependency(List<ModuleData> rawData) {

        List<ModuleData> mergeData = mergeModules(rawData);


        generateDependencyData(rawData, mergeData);


    }

/**
 * 
 * Generating dependency data(behavioral and data) for the rawData, 
 * which is list of data that consists of Module data of dependencies.
 * This method will create behavioral and data dependencyData, which we will use them to create matrix.
 * @param rawData
 * @param mergeData
 */
    private void generateDependencyData(List<ModuleData> rawData, List<ModuleData> mergeData) {
        for (ModuleData moduleData : mergeData) {

            Map<String, List<String>> mapReferences = moduleData.getReferences();
            String moduleName = moduleData.getModuleName();

            behavioralDependency.initDependency(moduleName);
            dataDependency.initDependency(moduleName);

            for (String key : mapReferences.keySet()) {


                List<String> items = mapReferences.get(key);
                insertDependency(moduleName, key, items, rawData);


            }
        }
    }
/**
 * Merge the nodes. If node exist, put the data on that node. 
 * this method was created in order to prevent duplicate node from c and h file.
 * for finch.c and finch.h, their data will be merged into one finch.
 * @param rawData
 * @return
 */
    private List<ModuleData> mergeModules(List<ModuleData> rawData) {

        List<ModuleData> mergeData = new ArrayList<ModuleData>();
        Boolean existFlag = false;
        for (ModuleData node : rawData) {
            for (ModuleData mergedNode : mergeData) {
                if (mergedNode.getModuleName().equals(node.getModuleName())) {
                    // The node already exist
                    mergedNode.merge(node);
                    existFlag = true;
                    break;

                }


            }
            if (!existFlag) {
                // node is not in the list yet

                mergeData.add(node);

            }
            existFlag = false;


        }


        return mergeData;  //To change body of created methods use File | Settings | File Templates.
    }

/**
 * Insert dependency in either behavioral or data dependencyData  based on the type of dependency between parent and child. 
 * If there exists behavioral dependency between two files, add them in behavioral dependencyData. Otherwise, put them into data dependencyData.
 * @param parent
 * @param child
 * @param items
 * @param rawData
 */
    private void insertDependency(String parent, String child, List<String> items, List<ModuleData> rawData) {
        ModuleData childNode = null;
        for (ModuleData module : rawData) {
            if (module.getModuleName().equals(child)) {
                childNode = module;
                break;
            }
        }

        if (childNode == null) {
            return;
        }

        Map<String, String> childDataObjects = childNode.getDataObjects();

        for (String item : items) {

            if (childDataObjects.containsKey(item)) {
                String type = childDataObjects.get(item);


                if (type.equals("function")) {
                    behavioralDependency.addDependency(parent, child);
                } else {
                    dataDependency.addDependency(parent, child);
                }

            }
        }

    }

/**
 * Initialization of the matrix
 * @return
 */
    public List<DependencyGraph> matrixifyInit() {
        List<DependencyGraph> graph = matrixHelper(rawInitialData);
        return graph;

    }
/**
 * make a matrix of dadaDependency and behavioralDependency
 * @param input
 * @return
 */
    private List<DependencyGraph> matrixHelper(List<ModuleData> input) {
        List<DependencyGraph> graph = new ArrayList<DependencyGraph>();

        dataDependency = new DependencyData(new HashMap<String, List<DependencyEdge>>(), DependencyType.DATA);
        behavioralDependency = new DependencyData(new HashMap<String, List<DependencyEdge>>(), DependencyType.BEHAVIOURAL);
        sortDataDependency(input);
        graph.add(new DependencyGraph(dataDependency));
        graph.add(new DependencyGraph(behavioralDependency));
        return graph;
    }
/**
 * Outputs the final version of the source code graphs.
 * @return
 */
    public List<DependencyGraph> matrixifyFinal() {
        List<DependencyGraph> graph = matrixHelper(rawFinalData);

        return graph;

    }


}


		







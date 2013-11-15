package dependency.viewer.mapper;

import java.util.*;

import dependency.viewer.parser.ModuleData;

public class SortDependencies {

    private DependencyData dataDependency;
    private DependencyData behavioralDependency;

    List<ModuleData> rawInitialData;
    List<ModuleData> rawFinalData;

    // Fill this in
    public SortDependencies(List<ModuleData> rawInitialData, List<ModuleData> rawFinalData) {
        this.rawInitialData = rawInitialData;
        this.rawFinalData = rawFinalData;


    }


    public void sortDataDependency(List<ModuleData> rawData) {

        List<ModuleData> mergeData = mergeModules(rawData);


        generateDependencyData(rawData, mergeData);


    }


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


    public List<DependencyGraph> matrixifyInit() {
        List<DependencyGraph> graph = matrixHelper(rawInitialData);
        return graph;

    }

    private List<DependencyGraph> matrixHelper(List<ModuleData> input) {
        List<DependencyGraph> graph = new ArrayList<DependencyGraph>();

        dataDependency = new DependencyData(new HashMap<String, List<DependencyEdge>>(), DependencyType.DATA);
        behavioralDependency = new DependencyData(new HashMap<String, List<DependencyEdge>>(), DependencyType.BEHAVIOURAL);
        sortDataDependency(input);
        graph.add(new DependencyGraph(dataDependency));
        graph.add(new DependencyGraph(behavioralDependency));
        return graph;
    }

    public List<DependencyGraph> matrixifyFinal() {
        List<DependencyGraph> graph = matrixHelper(rawFinalData);

        return graph;

    }


}


		






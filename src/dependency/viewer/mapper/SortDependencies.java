package dependency.viewer.mapper;

import java.util.*;

import dependency.viewer.Main;
import dependency.viewer.parser.ModuleData;

public class SortDependencies {

    static List<DependencyType> typeOfDataObject = new ArrayList<DependencyType>();
    static List<DependencyData> listDependencyData = new ArrayList<DependencyData>();
    private DependencyData dataDependency;
    private DependencyData behavioralDependency;

    // Fill this in
    public SortDependencies() {

        dataDependency = new DependencyData(new HashMap<String, List<DependencyEdge>>(), DependencyType.DATA);
        behavioralDependency = new DependencyData(new HashMap<String, List<DependencyEdge>>(), DependencyType.BEHAVIOURAL);
        listDependencyData.add(dataDependency);
        listDependencyData.add(behavioralDependency);

    }


    /*
     *  do:
     */
    public void sortDataDependency(List<ModuleData> rawData) {

        List<ModuleData> mergeData = mergeModules(rawData);


        for (ModuleData moduleData : mergeData) {

            Map<String, List<String>> mapReferences = moduleData.getReferences();
            String moduleName = moduleData.getModuleName();

            for (String key : mapReferences.keySet()) {
                //key is other module where the Module is referencing to.

                ///typeOfDataObject.clear();// to start off with fresh list

                List<String> items = mapReferences.get(key);
                // I will use dataOrBehavioral method to find out whether the string of the key has behavioral or data dependency
                insertDependency(moduleName, key, items, rawData);


            }
        }


        System.out.println("End of sort");
        /*
		 *  do:
		 */

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

                // TODO change this to enum

                if (type.equals("function")) {
                    behavioralDependency.addDependency(parent, child);
                } else {
                    dataDependency.addDependency(parent, child);
                }

            }
        }

    }

    public static void print() {

        for (int i = 0; i < listDependencyData.size(); i++) {

            System.out.println("list of DependencyData type" + listDependencyData.get(i).type);


            for (int e = 0; e < listDependencyData.get(i).getNodeMap().keySet().size(); e++) {
                // print out

                for (String key : listDependencyData.get(i).getNodeMap().keySet()) {
                    if (listDependencyData.get(i).getNodeMap().keySet().isEmpty()) {
                        break;
                    }

                    if (listDependencyData.get(i).getNodeMap().get(key) != null) {
                        System.out.println("\t" + "parent node of the key:" + listDependencyData.get(i).getNodeMap().get(key).get(e).getParentNode());
                        System.out.println("\t" + "1. child node of the key: " + listDependencyData.get(i).getNodeMap().get(key).get(e).getChildNode());
                        //	System.out.println("\t" +"2. keys stored in data: " + listDependencyData.get(i).getNodeMap().keySet())	;
                        System.out.println("\t" + "3. finch key values: " + listDependencyData.get(i).getNodeMap().get(key));

                    }
                }
            }
        }
    }


}


		






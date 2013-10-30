package dependency.viewer.parser;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: David
 * Date: 26/10/13
 * Time: 7:32 PM
 */
public class ModuleData {

    private String moduleName;
    private final List<String> includeList;
    private final List<String> includedByList;
    // key = file it depnds on, and the list what items are we using from that file ie functions, structs
    private final Map<String, List<String>> references;
    // TODO: Maybe we should change this to a emun of data types ie. define or function....
    // holds what the file provides
    private final Map<String, String> dataObjects;

    public ModuleData() {
        includedByList = new ArrayList<String>();
        includeList = new ArrayList<String>();
        references = new HashMap<String, List<String>>();
        dataObjects = new HashMap<String, String>();
    }

    public void insertInclude(String include) {
        includeList.add(include);
    }


    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public List<String> getIncludeList() {
        return includeList;
    }


    public List<String> getIncludedByList() {
        return includedByList;
    }


    public Map<String, List<String>> getReferences() {
        return references;
    }

    public Map<String, String> getDataObjects() {
        return dataObjects;
    }


    public void insertIncludedBy(String includedby) {
        includedByList.add(includedby);
    }

    public void addReference(String reference, String referencedFile) {

        if (!reference.equals(moduleName)) {    // check for self reference
            if (references.containsKey(reference)) {
                // it already exist, meaning a list is already in there
                references.get(reference).add(referencedFile);
            } else {
                // the entry doesnt exist yet and we need to create a list as well
                List<String> newList = new ArrayList<String>();
                newList.add(referencedFile);
                references.put(reference, newList);
            }
        }
    }

    public void print() {
        System.out.println("\nThe Module Name is : " + moduleName);

        if (!includeList.isEmpty())
            System.out.println("\nThe Includes are : ");
        for (String item : includeList) {
            System.out.println("    " + item);
        }

        if (!includedByList.isEmpty())
            System.out.println("\nThe Includes By are : ");
        for (String item : includedByList) {
            System.out.println("    " + item);
        }

        if (!dataObjects.isEmpty())
            System.out.println("\nThe Data Objects provided are : ");

        for (String key : dataObjects.keySet()) {
            System.out.println("    " + dataObjects.get(key) + "  :  " + key);
        }

        if (!references.isEmpty())
            System.out.println("\nThe referencces provided are : ");

        for (String key : references.keySet()) {
            System.out.println("    Reference to module :  " + key);
            for (String item : references.get(key)) {
                System.out.println("        :  " + item);
            }
        }

        System.out.println();
    }

    public void summerize() {
        System.out.println("\nThe Module Name is : " + moduleName);

        System.out.println("The number of other file references are : " + references.keySet().size());


        System.out.println();
    }

    public void putDataObject(String xmlContent, String type) {
        dataObjects.put(xmlContent, type);
    }


    /**
     * This function will return the list of types of data objects that are in this module
     * This is only a testing function, probably wont be needed in the future
     *
     * @return All data types
     */
    public Set<String> getDataObjectTypes() {
        Set<String> results = new HashSet<String>();
        for (String key : dataObjects.keySet()) {
            results.add(dataObjects.get(key));
        }
        return results;
    }

    public void merge(ModuleData node) {
        includeList.addAll(node.getIncludeList());
        includedByList.addAll(node.getIncludedByList());
        references.putAll(node.getReferences());
        dataObjects.putAll(node.getDataObjects());


    }
}

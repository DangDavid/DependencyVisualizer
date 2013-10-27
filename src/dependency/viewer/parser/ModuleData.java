package dependency.viewer.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: David
 * Date: 26/10/13
 * Time: 7:32 PM
 */
public class ModuleData {

    String moduleName;
    List<String> includeList;
    List<String> includedByList;
    // key = file it depnds on, and the list what items are we using from that file ie functions, structs
    Map<String, List<String>> references;
    // TODO: Maybe we should change this to a emun of data types ie. define or function....
    // holds what the file provides
    Map<String, String> dataObjects;

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
}

package dependency.viewer.parser;


import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

public class FileSearcher {

    private static final String FILE_EXT1 = "_8h.xml";
    private static final String FILE_EXT2 = "_8c.xml";

    public FileSearcher() {
    }


    public List<String> getFiles(String folderName) {


        String absPath = new File("").getAbsolutePath();
        String fileDir = absPath + File.separator + "src"
                + File.separator + "dependency"
                + File.separator + "viewer"
                + File.separator + folderName;


        // preparation
        GenericExtFilter filter = new GenericExtFilter();

        List<String> result = new ArrayList<String>();
        System.out.println(fileDir);
        File file = new File(fileDir);

        if (!file.isDirectory()) {
            System.out.println("Dir not found");
            return result;
        }

        // make list of all valid file names
        String[] list = file.list(filter);

        if (list.length == 0) {
            System.out.println("no files end with A or B : " + "A. " + FILE_EXT1 + " B. " + FILE_EXT2);
            return result;
        }


        for (String filename : list) {
            result.add(fileDir + File.separator + filename);
        }

        return result;
    }

    static public class GenericExtFilter implements FilenameFilter {


        public GenericExtFilter() {

        }

        public boolean accept(File dir, String name) {
            return (name.endsWith(FILE_EXT1) || name.endsWith(FILE_EXT2));
        }
    }

}

package dependency.viewer.parser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileSearcher {

    //private static final String FILE_DIR = "F:\\Downloads\\pidgin-2.10.7.tar\\pidgin-2.10.7\\pidgin-2.10.7\\finch\\xml";
    private static final String FILE_DIR = "/Users/taehyunkang/Downloads/pidgin-2.10.7/finch/xml";
    private static final String FILE_EXT1 = "_8h.xml";
    private static final String FILE_EXT2 = "_8c.xml";

    public FileSearcher() {
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        listFiles(FILE_DIR, FILE_EXT1, FILE_EXT2);
    }

    static public void listFiles(String dir, String FILE_EXT1, String FILE_EXT2) {

        // preparation
        GenericExtFilter filter = new GenericExtFilter(FILE_EXT1);
        File file = new File(dir);

        if (!file.isDirectory()) {
            System.out.println("Dir not found");
            return;
        }

        // make list of all valid file names
        String[] list = file.list(filter);

        if (list.length == 0) {
            System.out.println("no files end with A or B : " + "A. " + FILE_EXT1 + " B. " + FILE_EXT2);
            return;
        }

        for (String filename : list) {
            // append separator, formate results
            String temp = new StringBuffer(dir).append(File.separator)
                    .append(filename).toString();
            System.out.println("file : " + temp);
        }


    }

    public List<String> getFiles(String fileDir) {

        // preparation
        GenericExtFilter filter = new GenericExtFilter(FILE_EXT1);

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

        private String ext;

        public GenericExtFilter(String ext) {
            this.ext = ext;
        }

        public boolean accept(File dir, String name) {
            return (name.endsWith(FILE_EXT1) || name.endsWith(FILE_EXT2));
        }
    }

}

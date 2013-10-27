package dependency.viewer;

import dependency.viewer.parser.ModuleData;
import dependency.viewer.parser.XmlParser;

/**
 * Created with IntelliJ IDEA.
 * User: David
 * Date: 26/10/13
 * Time: 11:46 AM
 */
public class Main {


    public static void main(String[] args) {
        System.out.println("Hello World");
        XmlParser parser = new XmlParser();
        ModuleData m1 = parser.parseDocument("F:\\Downloads\\pidgin-2.10.7.tar\\pidgin-2.10.7\\pidgin-2.10.7\\finch\\xml\\finch_8c.xml");


        ModuleData m2 = parser.parseDocument("F:\\Downloads\\pidgin-2.10.7.tar\\pidgin-2.10.7\\pidgin-2.10.7\\finch\\xml\\finch_8h.xml");

        ModuleData m3 = parser.parseDocument("F:\\Downloads\\pidgin-2.10.7.tar\\pidgin-2.10.7\\pidgin-2.10.7\\finch\\xml\\getopt1_8c.xml");
        ModuleData m4 = parser.parseDocument("F:\\Downloads\\pidgin-2.10.7.tar\\pidgin-2.10.7\\pidgin-2.10.7\\finch\\xml\\gntwm_8c.xml");

        System.out.println("End of main");
    }
}

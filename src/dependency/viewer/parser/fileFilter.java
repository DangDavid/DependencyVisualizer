package filter;
import java.io.*;

public class fileFilter {

	private static final String FILE_DIR = "D:\\study\\Computer science\\cpsc 410\\oxygenResults\\xml";
	private static final String FILE_EXT1 = "_8h.xml";
	private static final String FILE_EXT2 = "_8c.xml";

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		listFiles(FILE_DIR, FILE_EXT1, FILE_EXT2);
	}

	static public void listFiles(String dir, String FILE_EXT1, String FILE_EXT2){

		// preparation 
		GenericExtFilter filter = new GenericExtFilter(FILE_EXT1);
		File file = new File(dir);

		if(!file.isDirectory()){
			System.out.println("Dir not found");
			return;
		}

		// make list of all valid file names
		String[] list = file.list(filter);

		if (list.length == 0) {
			System.out.println("no files end with A or B : " + "A. " + FILE_EXT1+" B. "+FILE_EXT2);
			return;
		}
 
		for (String filename : list) {
			// append separator, formate results
			String temp = new StringBuffer(dir).append(File.separator)
					.append(filename).toString();
			System.out.println("file : " + temp);
		}
	
	}
	
	static public class GenericExtFilter implements FilenameFilter {
		 
		private String ext;
 
		public GenericExtFilter(String ext) {
			this.ext = ext;
		}
 
		public boolean accept(File dir, String name) {
			return (name.endsWith(FILE_EXT1)||name.endsWith(FILE_EXT2));
		}
	}

}

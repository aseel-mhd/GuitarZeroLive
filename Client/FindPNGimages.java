import java.io.*;
/*
 * FindPNGimages.
 * 
 * Locates all PNG images within a folder and prints out the path
 *
 * @version 1.00, February 2019.
 */
public class FindPNGimages {
  	// Specifies the path folder containing the cover art
    	// String path = System.getProperty("user.dir");
  	private static final String fileLocation = "/home/links/am1199/Desktop/ecm2434/Music Bundles/"; // TODO figure out how to set to current directory

  	// Specifies that the wanted image type is PNG
  	private static final String searchThisExtn = ".png";

  	public static void main(String[] args) {
    		FindPNGimages obj = new FindPNGimages();
    		obj.listFiles(fileLocation, searchThisExtn);
  	}

    /*
     * Function for printing the list of files with PNG extension
     * @param loc, the location of the file
     * @param extn, the extension type of the file
     */
	  public void listFiles(String loc, String extn) {

    		SearchFiles files = new SearchFiles(extn); // searches all the files to locate the ones with PNG extension
    		File folder = new File(loc);

    		if(folder.isDirectory() == false) {
      			System.out.println("Folder does not exists: " + fileLocation);
      			return;
    		}

    		String[] list = folder.list(files);

    		if (list.length == 0) {
      			System.out.println("There are no files with " + extn + " Extension");
      			return;
    		}

    		for (String file : list) {
      			String temp = new StringBuffer(fileLocation).append(File.separator).append(file).toString();
      			System.out.println("" + temp);
    		}

	  }

	  public class SearchFiles implements FilenameFilter {

		    private String ext;
    		public SearchFiles(String ext) {
    			   this.ext = ext;
    		}

    		@Override
    		public boolean accept(File loc, String name) {
    			  if(name.lastIndexOf('.')>0) {
                // Get last index for '.'
                int lastIndex = name.lastIndexOf('.');

                // Get extension
                String str = name.substring(lastIndex);

                // Matching extension
                if(str.equalsIgnoreCase(ext)) {
                      return true;
                }
            }
            return false;
    		}
	  }
}

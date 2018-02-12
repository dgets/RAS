import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Util {
	public static final String[] flagExtensions = { ".tar", ".tar.gz", ".tgz", ".tar.bz2", 
	                                                ".tar.xz", ".zip", ".arj", ".tar.Z" }; 
	/**
	 * Flag archives in the directory listing (by extension, not signature) in order to mark
	 * them for later unrolling or other processing
	 */
	/*public static HashMap<String, Boolean> flagArchives(HashMap<String, Boolean> directory) {
		List<String> internalArchives = new ArrayList<String>();
		Boolean hit = false;
		
		for (String entry : directory) {
			for (String ext : flagExtensions) {
				if (entry.toLowerCase().endsWith(ext)) {
					hit = true;
					break;
				}
			}
			
			if (hit) {
				directory.put(entry, true);
			}
		}
		
		return internalArchives;
	}*/
	
	public static Boolean runningOnDoze() {
		return System.getProperty("os.name").startsWith("Windows");
	}
	
	/**
	 * Unroll archives (including recursive archival)
	 */
	
}

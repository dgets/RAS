import java.util.ArrayList;
import java.util.List;

public class Archive {
	public static final String[] flagExtensions = { "tar", "tar.gz", "tgz", "tar.bz2", 
	                                                "tar.xz" }; 
	/**
	 * Flag archives in the directory listing (by extension, not signature) in order to mark
	 * them for later unrolling or other processing
	 */
	public static List<String> flagArchives(List<String> directory) {
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
				internalArchives.add(entry);
			}
		}
		
		return internalArchives;
	}
}

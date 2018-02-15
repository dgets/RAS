import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Util {
	public static final String[] flagExtensions = { ".tar", ".tar.gz", ".tgz", ".tar.bz2", 
	                                                ".tar.xz", ".zip", ".arj", ".tar.Z" };
	public static final String[] archiveExtensions = { ".tar", ".zip", ".arj" };
	public static final String[] compressExtensions = { ".gz", ".tgz", ".bz2", ".xz", ".zip",
														".arj", ".Z" };
	
	private static final int decompressionFactor = 2;
	
	public static Boolean runningOnDoze() {
		return System.getProperty("os.name").startsWith("Windows");
	}
	
	public static void wipeDirectory(File toastThis) throws IOException {
		toastThis.deleteOnExit();
	}
	
	public static void wipeDirectoryNow(File toastThis) throws IOException {
		toastThis.delete();
	}
	
	public static HashMap<String, Boolean> findEmbedded(ArrayList<String> contents) {
		HashMap<String, Boolean> contentFlags = new HashMap<String, Boolean>();
		Boolean hit;
		
		for (String entry : contents) {
			hit = false;
			
			for (String extension : flagExtensions) {
				if (entry.toLowerCase().endsWith(extension)) {
					contentFlags.put(entry, true);
					hit = true;
					break;
				}
			}
			if (!hit) {
				contentFlags.put(entry, false);
			}
		}
		
		return contentFlags;
	}
	
	public static Boolean adequateDriveSpace(MyArchive archive, String partitionPath) {
		File tmpDir = new File(System.getProperty("java.io.tmpdir"));
		
		return (tmpDir.getFreeSpace() >= 
			(archive.getArchiveSource().length() * decompressionFactor));
	}
	
	public static void unrollNextArchives(MyArchive currentArchive) throws Exception {
		//ArrayList<MyArchive> nextArchives = new ArrayList<MyArchive>();
		
		for (MyArchive ouah : currentArchive.getInternalArchives()) {
			try {
				ouah.unroll(RAS.KEEP_GOING);
			} catch (Exception ex) {
				throw new Exception("Oh shit: " + ex.getMessage());
			}
		}
	}
	
	public static void cleanUpMess(MyArchive wipeExpandedArchive) {
		
	}
}

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Collection of utilities for working with MyArchive entries.
 *  
 * @author Damon Getsman
 *
 */
public class Util {
	public static final String[] flagExtensions = { ".tar", ".tar.gz", ".tgz", ".tar.bz2", 
	                                                ".tar.xz", ".zip", ".arj", ".tar.Z" };
	public static final String[] archiveExtensions = { ".tar", ".zip", ".arj" };
	public static final String[] compressExtensions = { ".gz", ".tgz", ".bz2", ".xz", ".zip",
														".arj", ".Z" };
	
	private static final int decompressionFactor = 2;
	
	/**
	 * Determines whether or not the system is windows.
	 * 
	 * @return Boolean
	 */
	public static Boolean runningOnDoze() {
		return System.getProperty("os.name").startsWith("Windows");
	}
	
	/**
	 * Sets directory to be wiped upon program exit.
	 * 
	 * @param toastThis File
	 * @throws IOException
	 */
	public static void wipeDirectory(File toastThis) throws IOException {
		toastThis.deleteOnExit();
	}
	
	/**
	 * Wipes directory contents immediately.
	 * 
	 * @param toastThis File
	 * @throws IOException
	 */
	public static void wipeDirectoryNow(File toastThis) throws IOException {
		toastThis.delete();
	}
	
	/**
	 * Determines, by extension only, what entries in the contents listing are
	 * embedded archives.  Produces a hashmap with the appropriate flag and
	 * returns it.
	 * 
	 * @param contents ArrayList
	 * @return HashMap
	 */
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
	
	/**
	 * Determines whether or not there is adequate drive space remaining for
	 * the specified archive to be decompressed in the temp directory 
	 * specified by the applicable system property.
	 * 
	 * @param archive MyArchive
	 * @param partitionPath String
	 * @return Boolean
	 */
	public static Boolean adequateDriveSpace(MyArchive archive, String partitionPath) {
		File tmpDir = new File(System.getProperty("java.io.tmpdir"));
		
		return (tmpDir.getFreeSpace() >= 
			(archive.getArchiveSource().length() * decompressionFactor));
	}
	
	/**
	 * Unrolls the archives specified within the passed archive.
	 * 
	 * @param currentArchive MyArchive
	 * @throws Exception
	 */
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
	
	/**
	 * Cleans up (utilizing wipeDirectory()) the temp directory dingleberries
	 * left hanging around by the specified archive.
	 * 
	 * @param wipeExpandedArchive MyArchive
	 */
	public static void cleanUpMess(MyArchive wipeExpandedArchive) {
		
	}
}

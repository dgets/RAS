public class Util {
	public static final String[] flagExtensions = { ".tar", ".tar.gz", ".tgz", ".tar.bz2", 
	                                                ".tar.xz", ".zip", ".arj", ".tar.Z" };
	public static final String[] archiveExtensions = { ".tar", ".zip", ".arj" };
	public static final String[] compressExtensions = { ".gz", ".tgz", ".bz2", ".xz", ".zip",
														".arj", ".Z" };
	
	public static Boolean runningOnDoze() {
		return System.getProperty("os.name").startsWith("Windows");
	}
}

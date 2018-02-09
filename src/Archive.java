import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

/**
 * 
 * @author sprite
 *
 */
public class Archive {
	private String 	arcFileName;
	private Path 	unrollPath;
	private Boolean	containsArchives;
	
	public Archive(String fn) throws Exception {
		File	archiveSource							=	new File(fn);
		Boolean	stupidFlag								=	false;
		Set<PosixFilePermission> perms					=
			PosixFilePermissions.fromString("rwx------");
		FileAttribute<Set<PosixFilePermission>> attr 	=
			PosixFilePermissions.asFileAttribute(perms);
		
		if (!archiveSource.isFile()) {
			throw new Exception("Invalid archive source: " + fn);
		}
		this.arcFileName = fn;
		
		for (String extension : Util.flagExtensions) {
			if (this.arcFileName.toLowerCase().endsWith(extension)) {
				stupidFlag = true;
				break;
			}
		}
		if (!stupidFlag) {
			throw new Exception("Invalid archive source file extension");
		}
		
		try {
			unrollPath = Files.createTempDirectory(RAS.tmpDir, attr);
		} catch (Exception ex) {
			throw new Exception("Issue creating temp dir: " + ex.toString());
		}
	}
}

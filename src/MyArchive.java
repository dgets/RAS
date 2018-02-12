import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.HashMap;
import java.util.Set;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;

/**
 * 
 * @author sprite
 *
 */
public class MyArchive {
	private String 	arcFileName;
	private Path 	unrollPath;
	private Boolean	containsArchives;
	private File	archiveSource;
	
	Set<PosixFilePermission> perms =
		PosixFilePermissions.fromString("rwx------");
	FileAttribute<Set<PosixFilePermission>> attr =
		PosixFilePermissions.asFileAttribute(perms);
	
	public MyArchive(String fn) throws Exception {
		this.archiveSource = new File(fn);
		
		if (!archiveSource.isFile()) {
			throw new Exception("Invalid archive source: " + fn);
		}
		
		this.arcFileName = fn;
	}
	
	//getters/setters
	public Path getUnrollPath() {
		return this.unrollPath;
	}
	
	public Boolean getContainsArchives() {
		return this.containsArchives;
	}
	
	public String getArcFileName() {
		return this.arcFileName;
	}
	
	public File getArchiveSource() {
		return this.archiveSource;
	}
	
	//methods
	public HashMap<String, Boolean> getEntryHash() throws Exception {
		HashMap<String, Boolean> entryData = new HashMap<String, Boolean>();
		ArchiveEntry entry = null;
		InputStream fis = new BufferedInputStream(
			Files.newInputStream(this.archiveSource.toPath()));
		ArchiveInputStream ais = 
			new ArchiveStreamFactory().createArchiveInputStream(fis);
		
		try {
			unrollPath = Files.createTempDirectory("RAS");
		} catch (Exception ex) {
			fis.close(); ais.close();
			throw new Exception("Issue creating temp dir: " + ex.toString());
		}
		
		try {
			Boolean hit = false;
			
			while ((entry = ais.getNextEntry()) != null) {
				for (String ext : Util.flagExtensions) {
					if (entry.getName().toLowerCase().endsWith(ext)) {
						hit = true;
						break;
					}
				}
				entryData.put(entry.getName(), hit);
				hit = false;
			}
		} catch (Exception ex) {
			fis.close(); ais.close();
			throw new Exception("Issue getting entry names: " + ex.toString());
		}
		
		fis.close(); ais.close();
		return entryData;
	}
}

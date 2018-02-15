import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.utils.IOUtils;

/**
 * 
 * @author sprite
 *
 */
public class MyArchive {
	private String 	arcFileName;	//not sure if this is really necessary
	private Path 	unrollPath;
	private Boolean	containsArchives;
	private File	archiveSource;
	private ArrayList<String>	archiveContents;
	private HashMap<String, Boolean>	internalArchives;

	Set<PosixFilePermission> perms =
		PosixFilePermissions.fromString("rwx------");
	FileAttribute<Set<PosixFilePermission>> attr =
		PosixFilePermissions.asFileAttribute(perms);
	
	public MyArchive(String fn) throws Exception {
		this.archiveContents = null;
		this.unrollPath = null;
		this.arcFileName = fn;
		this.archiveSource = new File(fn);
		
		if (!archiveSource.isFile()) {
			throw new Exception("Invalid archive source: " + fn);
		}
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
	private ArchiveInputStream getArchiveInputStream() throws Exception {
		InputStream fis = new BufferedInputStream(
			Files.newInputStream(this.archiveSource.toPath()));
		
		return new ArchiveStreamFactory().createArchiveInputStream(fis);
	}
	
	private void setUnrollPath() throws Exception {
		if (unrollPath == null && !Util.runningOnDoze()) {
			unrollPath = Files.createTempDirectory("RAS_", attr);
		} else if (unrollPath == null) {
			unrollPath = Files.createTempDirectory("RAS_");
		}
	}
	
	public HashMap<String, Boolean> getEntryHash() throws Exception {
		HashMap<String, Boolean> entryData = new HashMap<String, Boolean>();
		ArchiveEntry entry = null;
		ArchiveInputStream ais = getArchiveInputStream();
		
		try {
			setUnrollPath();
		} catch (Exception ex) {
			ais.close();
			throw new Exception("Issue creating temp dir: " + ex.toString());
		}
		
		try {
			//Boolean hit = false;
			
			while ((entry = ais.getNextEntry()) != null) {
				/*for (String ext : Util.flagExtensions) {
					if (entry.getName().toLowerCase().endsWith(ext)) {
						hit = true;
						break;
					}
				}*/
				archiveContents.add(entry.getName());
				/*entryData.put(entry.getName(), hit);
				hit = false;*/
			}
		} catch (Exception ex) {
			ais.close();
			throw new Exception("Issue getting entry names: " + ex.toString());
		}
		
		ais.close();
		entryData = Util.findEmbedded(archiveContents);
		
		return entryData;
	}
	
	public HashMap<String, Boolean> unroll() throws Exception {
		HashMap<String, Boolean> entryData = new HashMap<String, Boolean>();
		ArchiveEntry entry;
		File currentFile, parentFile;
		
		ArchiveInputStream ais = getArchiveInputStream();
		
		setUnrollPath();
		
		if (RAS.VERBOSE) {
			System.out.println("Attempting to expand " + arcFileName + 
				" to " + unrollPath);
		}
		
		while ((entry = ais.getNextEntry()) != null) {
			archiveContents.add(entry.getName());
			
			if (entry.isDirectory()) {
				continue;
			}
			
			currentFile = new File(unrollPath.toFile(), entry.getName());
            parentFile = currentFile.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            
            IOUtils.copy(ais, new FileOutputStream(currentFile));
		}
		entryData = Util.findEmbedded(archiveContents);
		
		System.out.println(arcFileName + " contents:");
		if (Debugging.UNROLL) {
			//list directory contents
			for (String entryName : archiveContents) {
				System.out.println(entryName);
			}
		}
		
		
	}
}

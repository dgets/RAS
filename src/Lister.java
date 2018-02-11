import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.compress.archivers.*;

public final class Lister {
    private static final ArchiveStreamFactory factory = new ArchiveStreamFactory();

	@SuppressWarnings("null")
	public static List<String> getEntriesList(final String fn) throws Exception {
    	if (Debugging.LISTER) {
    		System.out.println("Analyzing " + fn + ". . .");
    	}
        
        final File f = new File(fn);
        
        if (!f.isFile()) {
        	//as we're conscripting this for our own purposes, might as well throw an exception
        	//here, I suppose
            System.err.println(f.getName() + " doesn't exist or is a directory");
        }
        
        //okay, so I do understand this syntax now, but I really must say, it reduces the shit
        //out of code readability, as far as I'm concerned; it's not originally my code, at
        //least, so I'm not going to worry about its aesthetics.  It's all on you, apache...
        List<String> directory = new ArrayList<String>();
        try (final InputStream fis = new BufferedInputStream(Files.newInputStream(f.toPath()));
                final ArchiveInputStream ais = factory.createArchiveInputStream(fis)) {
        	if (RAS.VERBOSE) {
        		System.out.println("Created " + ais.toString());
        	}
        	
            ArchiveEntry ae;
            while ((ae = ais.getNextEntry()) != null) {
                if (RAS.VERBOSE) {
                	System.out.println(ae.getName());
                }
                directory.add(ae.getName());
            }
        }
        
        return directory;
    }
}
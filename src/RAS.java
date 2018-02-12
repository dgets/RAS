import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;


/**
 * RAS - Recursive Archive Scanner
 * This project is a restart of one that I'd been working on previously,
 * RecScan.  I realized with that project that I should've read the API docs
 * for the Apache commons compress library that I'm utilizing with it a lot
 * better, as I was recreating the wheel with basically every line of code
 * that I'd been working on at this point.  Let's try to avoid that this time,
 * shall we?
 */

/**
 * @author Damon Getsman
 * started: 5 Feb 18
 * finished:
 * 
 * @version 0.1a
 *
 */
public class RAS {
	public static final boolean VERBOSE			= 	true;
	public static final boolean UNROLL_FIRST	=	false;
	public static final boolean USING_LISTER	= 	false;	//mine or apache's?
	
	//public static final String tmpDir			= 	new String("/tmp");
	//public static final String tmpDir			=	System.getProperty("java.io.tmpdir")
	//		+ "/RAS";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length == 0 || (args.length == 1 && args[0].equals("-v"))) {
			//dump usage
            usage();
            return;
        } else if (args.length == 1 && !args[0].equals("-v")) {
        	//list contents
        	if (USING_LISTER) {
        		List<String> directory = new ArrayList<String>();
        		
        		try {
        			directory = Lister.getEntriesList(args[0]);
        		} catch (Exception e) {
        			e.printStackTrace();
        		}
        	
        		System.out.println(directory.toString());
        	} else {
        		HashMap<String, Boolean> directory = 
        			new HashMap<String, Boolean>();
        		
        		try {
        			MyArchive archive = new MyArchive(args[0]);
        			directory = archive.getEntryHash();
        		} catch (Exception ex) {
        			System.err.println("MyArchive Error: " + 
        				ex.getMessage());
        		}
        		
        		for (String entry : directory.keySet()) {
        			if (directory.get(entry)) {
        				System.out.print(" * ");
        			} else {
        				System.out.print("   ");
        			}
        			System.out.println(entry);
        		}
        	}
        } else {
        	//we're not there yet
        	System.out.println("Not supported yet . . .");
        	usage();
        }

	}
	
    private static void usage() {
        System.out.println("Parameters: [-v] archive-name");
    }

    private static void displayEntryData(HashMap<String, Boolean> eData) {
    	Set archiveEntries = eData.keySet();
    	Iterator entry = eData.keySet().iterator();
    	
    	if (VERBOSE) {
    		System.out.println(
    				"Entry Text (starred if flagged as recursive archive)\n" +
    				"----------------------------------------------------");
    	}
    	
    	while (entry.hasNext()) {
    		HashMap.Entry<String, Boolean> ouah = 
    				(Entry<String, Boolean>) entry.next();
    	}
    }
    
}

import java.util.HashMap;

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
	public static final boolean USING_LISTER	= 	false;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length == 0 || (args.length == 1 && args[0].equals("-v"))) {
			//dump usage
            usage();
            return;
        } else if (args[0].equals("-v")) {
        	System.err.println("Sorry, verbose is set by default at this " +
        		"point in development.\n");
        	usage();
        	return;
        } else if (args.length == 1 /*&& !args[0].equals("-v")*/) {
        	//list contents
        	HashMap<String, Boolean> directory = 
        		new HashMap<String, Boolean>();

        	try {
        		MyArchive archive = new MyArchive(args[0]);
        		directory = archive.getEntryHash();
        	} catch (Exception ex) {
        		System.err.println("MyArchive Error: " + 
        			ex.getMessage());
        	}

        	displayEntryData(directory);
        } else if (args.length == 2 && args[0].equals("-x")) {
        	//expand contents
        	try {
        		MyArchive archive = new MyArchive(args[1]);
        		archive.unroll();
        	} catch (Exception ex) {
        		System.err.println("MyArchive Error: " + 
        			ex.getMessage());
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
    	if (VERBOSE) {
    		System.out.println(
    				"Entry Text (starred if flagged as recursive archive)\n" +
    				"----------------------------------------------------");
    	}
    	
    	for (String entry : eData.keySet()) {
    		if (eData.get(entry)) {
    			System.out.print(" * ");
    		} else {
    			System.out.print("   ");
    		}
    		System.out.println(entry);
    	}
    }
    
}

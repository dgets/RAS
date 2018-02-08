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
        	try {
				Lister.listEntries(args[0]);
			} catch (Exception e) {
				e.printStackTrace();
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

}

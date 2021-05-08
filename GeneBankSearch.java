import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class GeneBankSearch {
	
	
    public static void main(String[] args) {

        int cacheSize = 0;
        boolean cacheImp = false;
        if(args.length != 4) {
            printUsage();
            return;
        }

        //setting the cacheImp, via the first arg
        if(args[0].equals("1")) { cacheImp = true; }
        //if the first arg isn't 0 or 1, printUsage
        //else if (args[0] != "0" && args[0] != "1") { printUsage(); }

         
        boolean userCache = false;
		//reading in the cache size
        //if(userCache  == true && args.length >= 4) { cacheSize = Integer.parseInt(args[3]); }


        //reading in the BTree Files
        try{

            //Reading in the files referenced in the arguments
            File bTreeFile = new File(args[1]);
            File queryFile = new File(args[2]);

            //Scanner for the bTreeFile
            Scanner scan = new Scanner(bTreeFile);
            //A tree to store the values read in by the scanner
            int degree = Integer.parseInt(args[1].substring(args[1].lastIndexOf(".") + 1));
            BTree storedTree = new BTree(degree, args[1]);

            ArrayList<BTree.BTreeNode> parents = new ArrayList<BTree.BTreeNode>();

            
            /**
             * Searching the query file
             */
            Scanner qFileScan = new Scanner(queryFile);
            char input;
            long binaryValue = 0;
            long key = 0;
            String nextLine = "";
            while(qFileScan.hasNext()){
                nextLine = qFileScan.nextLine();
                key = 0;
                //storing the letters as binary numbers
                for(int i = 0; i < nextLine.length(); i++) {
        			if(nextLine.toUpperCase().charAt(i) == 'A') {
        				binaryValue = 0;
        			}
        			if(nextLine.toUpperCase().charAt(i) == 'T') {
        				binaryValue = 3;
        			}
        			if(nextLine.toUpperCase().charAt(i) == 'C') {
        				binaryValue = 1;
        			}
        			if(nextLine.toUpperCase().charAt(i) == 'G') {
        				binaryValue = 2;
        			}
        			key |= binaryValue << (i * 2);
        		}

                BTree.TreeObject sequenceQuery = storedTree.BTreeSearch(storedTree.getRoot(), key);
                System.out.println(sequenceQuery.getFrequency() + " " + nextLine);
                
            }   


        } catch( Exception e) { 
            e.printStackTrace();
        }
    }


    /**
     * PrintUsage() Method for when the user doesn't correctly enter the arguments
     */
    private static void printUsage(){
        System.out.println("Usage: java GeneBankSearch <0/1 (no/with Cache)> <BTree file> <Query file> <cache size> [<debug level>] ");
        System.exit(1);
    }
}

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import BTree.BTreeNode;


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
        else if (args[0] != "0" && args[0] != "1") { printUsage(); }

         
        boolean userCache = false;
		//reading in the cache size
        if(userCache  == true && args.length >= 4) { cacheSize = Integer.parseInt(args[3]); }


        //reading in the BTree Files
        try{

            //Reading in the files referenced in the arguments
            File bTreeFile = new File(args[1]);
            File queryFile = new File(args[2]);

            //Scanner for the bTreeFile
            Scanner scan = new Scanner(bTreeFile);
            //A tree to store the values read in by the scanner
            BTree storedTree = new BTree(3);

            ArrayList<BTreeNode> parents = new ArrayList<BTree>();

            
            /**
             * Searching the query file
             */
            Scanner qFileScan = new Scanner(queryFile);
            char input;
            String key = "" ;
            while(qFileScan.hasNext()){
                String nextLine = qFileScan.next();
                //storing the letters as binary numbers
                for(int i = 0; i<nextLine.length(); i++) {
                    
                    input = nextLine.charAt(i);
                    if(input == 'a' || input =='A') { 
                        key += "00";
                    } else if(input == 'c' || input == 'C') {
                        key += "01";
                    } else if(input == 'g' || input == 'G') {
                        key += "10";
                    } else if(input == 't' || input == 'T') {
                        key += "11";
                    }

                }

                BTreeNode rootNode = storedTree.searchKey(storedTree.getRoot(), key);

                if(rootNode != null) { System.out.println(key); }

                //resets the key placeholder
                key = "";
            }   
             
            /**
             * Scanning the BTree File
             */
            while(scan.hasNextLine()){
                String line = scan.nextLine();
                Scanner lineScan = new Scanner(line);

                lineScan.useDelimiter(",");
                int parentIndex = lineScan.nextInt();

                BTreeNode newNode = new BTreeNode();

                //while there is still keys in the line, add it to the new Node
                while(lineScan.hasNext()) {
                    newNode.addKey(lineScan.next());
                }

                //add the new node to the list of parents
                parents.add(newNode);

                //if the parent isn't the first parent of the node, then decrease it's index, else create a new BTree
                if(parentIndex != 0){
                    parentIndex = parentIndex - 1;
                    parents.get(parentIndex).addChild(newNode);
                } else {
                    storedTree = new BTree(newNode);
                }
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

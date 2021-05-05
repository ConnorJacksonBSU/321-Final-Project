import java.util.Scanner;
import java.util.NoSuchElementException;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;

public class GeneBankCreateBtree {
    static final char[] usedChars = new char[] {'A', 'T', 'C', 'G', 'N', 'a', 't', 'c', 'g', 'n'};

    public static void main(String[] args) {

        boolean debugged;
        boolean cache;
        int cacheSize;

        if (args.length < 5 || args.length > 6) {
            printUsage();
            return;
        }
    

    int cacheOne = Integer.parseInt(args[0]);
    cache = cacheOne != 0;

    int degree = Integer.parseInt(args[1]);

    if (degree == 0) degree = 102;

    String geneBankFile = args[2];

    int geneSequenceLength = Integer.parseInt(args[3]);

    if (geneSequenceLength > 31 || geneSequenceLength < 1) {
        System.out.println("The length of the sequence is out of bounds. must be within 1-31.");
        return;
    }

    if (cache) {
        cacheSize = Integer.parseInt(args[4]);
        if (cacheSize == 0) cache = false;

    } else {
        cacheSize = -1;
    }

    String[] geneBankFileSplit = geneBankFile.split("/");
    String bTreeFileName = geneBankFileSplit[geneBankFileSplit.length-1] + ".btree.data." + geneSequenceLength + "." + degree;

    if(args.length > 4) {
        if(Integer.parseInt(args[args.length -1]) == 0) {
            debugged = false;
        
        } else {
            debugged = true;
        }

    } else {
        debugged = false;
    }

    Btree treeOne = new Btree(degree, geneSequenceLength, bTreeFileName, cache, cacheSize);

    Scanner scanner;

    try {
        scanner = new Scanner(new File(geneBankFile));
    }






    
    }
}

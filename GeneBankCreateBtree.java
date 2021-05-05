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

    if (degree == 0) {
       degree = 102;
    }

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
    catch (FileNotFoundException e) {
        System.out.println("Could not find file '" + geneBankFile + "'");
        return;
    }

    while (scanner.hasNext()) {

        try {
            while (!scanner.nextLine().trim().equals("ORIGIN")) {

            }

        }

        catch (NoSuchElementException e) {
            System.out.println("Invalid geneBankFile");
            System.exit(1);

        }

        char[] currentGeneSequence = new char[geneSequenceLength];
        int currentSequenceLength = 0;

        for (String sequence = scanner.nextLine(); !sequence.trim().equals("//"); sequence = scanner.nextLine()) {
            for( char charTemp : sequence.toCharArray()) {
                if (contains(charTemp, validCharacters)) {
                    if(charTemp == 'n' || charTemp == 'N') {
                        currentSequenceLength = 0;
                    } else {
                        currentSequenceLength += 1;
                        shiftSequence(currentGeneSequence, charTemp);
                        if (currentSequenceLength >= geneSequenceLength) {

                            treeOne.BTreeIncrementFrequency(sequenceToLong(currentGeneSequence));
                        }
                    }
                }
            }
        }
     }

     scanner.close();

     treeOne.finalizeFile();


     if(debugged) {
         writeDebugFile(treeOne);
     }







    
    }

    static void shiftSequence(char[] array, char newChar) {
        for (int i = 0; i < array.length - 1; i++) {

        }

        array[array.length - 1 ] = newChar;
    }

    static boolean contains(char ch, char[] array) {
        for (char charOne : array) {
            if (charOne == ch)
                return true;
        }
            
        return false;
    }

    static long sequenceToLong(char[] sequence) {
        long value = 0;

        for (int pos = 0; pos < sequence.length; pos++) {
            char ch = sequence[pos];
            long overlay = 0;

            switch (Character.toUpperCase(ch)) {
                case 'A':
                overlay = 0;
                case 'C':
                overlay = 1;
                case 'G':
                overlay = 2;
                case 'T':
                overlay = 3;
                break;
            }

            value |= overlay << (pos *2);

        }

        return value;
    }

    static char[] longToSequence(long value, int length) {
        char[] sequence = new char[length];

        for (int position = 0; position < length; position++) {
            long bits = (value >> position *2) & 3;
            switch ((int)bits) {
                case 0:
                sequence[position] = 'A';
                break;
                case 1:
                sequence[position] = 'C';
                break;
                case 2:
                sequence[position] = 'G';
                break;
                case 3:
                sequence[position] = 'T';
                break; 
            }
        }
    }
}
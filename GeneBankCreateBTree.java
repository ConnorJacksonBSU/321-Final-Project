/**
* Takes in a Gene Bank file, and stores it in a BTree
* @author Andrew Moorcroft, Connor Jackson, Jackson Looney
*/
import java.io.File;  
import java.io.FileWriter;
import java.io.FileNotFoundException; 
import java.util.Scanner; 


public class GeneBankCreateBTree {
	private int degree;
	private int seqLength;
	private String file;
	private BTree seqBtree;
	
	GeneBankCreateBTree(int degree, int seqLength, String file){
		this.degree = degree;
		this.seqLength = seqLength;
		this.file = file;
		this.seqBtree = new BTree(degree, seqLength);
	}
	
	public static void main(String []args) {
		int argDegree;
		int argSeqLength;
		int debuglvl;
		String gbkFile;
		String dataFileName = "";
		
		if(args.length > 6) {
			printUsage();
			System.exit(-1);
		}
		
		argDegree = Integer.parseInt(args[1]);
		gbkFile = args[2];
		argSeqLength = Integer.parseInt(args[3]);
		if(argSeqLength < 1 || argSeqLength > 31) {
			System.out.println("Error: Sequecne length must be between 1 and 31 inclusively");
			System.exit(-1);
		}
		debuglvl = Integer.parseInt(args[5]);
		
		if(gbkFile.lastIndexOf('/') != -1) {
			dataFileName = gbkFile.substring(gbkFile.lastIndexOf('/') + 1);
		} else {
			dataFileName = gbkFile;
		}
		dataFileName += ".btree.data." + argSeqLength + "." + argDegree;
		
		GeneBankCreateBTree myGeneBank = new GeneBankCreateBTree(argDegree,argSeqLength, gbkFile);
		myGeneBank.parseFile(debuglvl, dataFileName);
	}
	
	private void parseFile(int debug, String dataFileName) {
		try {
			File geneSeqFile = new File(this.file);
			Scanner fileScanner = new Scanner(geneSeqFile);
			String fileString = fileScanner.next();
			String parseString = "";
			long binarySequence = 0;
			long longRep = 0;
			
			while(fileScanner.hasNext()){
				while(fileScanner.hasNext() && !fileString.equals("ORIGIN")) {
					fileString = fileScanner.next();
				}
				
				fileString = fileScanner.next();
				fileString = "";
				int counter = 1;
				while(fileScanner.hasNext() && !fileScanner.hasNext("//")) {
					if(counter != 7) {
						fileString += fileScanner.next();
						counter++;
					} else {
						fileScanner.next();
						counter = 1;
					}
				}
				fileScanner.next();
				
				parseString = fileString.substring(0, this.seqLength);
				longRep = convertSequenceToLong(parseString);
				seqBtree.BTreeInsert(longRep);
				
				for(int i = this.seqLength; i < fileString.length(); i++) {
					if(fileString.toUpperCase().charAt(i)!= 'N') {
						parseString = parseString.substring(1, this.seqLength);
						parseString += fileString.charAt(i);
						longRep = convertSequenceToLong(parseString);
						BTree.TreeObject findTreeObject = seqBtree.BTreeSearch(seqBtree.getRoot(), longRep);
						System.out.println();
						if(findTreeObject == null) {
							seqBtree.BTreeInsert(longRep);
						} else{
							findTreeObject.increaseFrequency();
						}
					}
				}
				String outString = seqBtree.dumpBTree(seqBtree.getRoot());
				if(debug == 1) {
					FileWriter writer = new FileWriter("Dump.txt");
					writer.write(outString);
					writer.close();
				}
				FileWriter writer = new FileWriter(dataFileName);
				writer.write(outString);
				writer.close();
				
			}
			fileScanner.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private long convertSequenceToLong(String sequence) {
		long binarySequence = 0;
		long longValue = 0;;
		for(int i = 0; i < sequence.length(); i++) {
			if(sequence.toUpperCase().charAt(i) == 'A') {
				binarySequence = 0;
			}
			if(sequence.toUpperCase().charAt(i) == 'T') {
				binarySequence = 3;
			}
			if(sequence.toUpperCase().charAt(i) == 'C') {
				binarySequence = 1;
			}
			if(sequence.toUpperCase().charAt(i) == 'G') {
				binarySequence = 2;
			}
			longValue |= binarySequence << (i * 2);
		}
		
		return longValue;
	}
	
	public static void printUsage() {
		System.out.println("Usage: GeneBankCreateBTree <0/1(no/with Cache)> <degree> <gbk file> <sequence length> <cache size> [<debug level>]");
	}
}

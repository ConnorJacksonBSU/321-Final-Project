import java.util.ArrayList;
import java.util.Comparator;
import java.io.IOException;

public class BTree {

    //root of the BTree
    private BTreeNode root;
    // t
	private int degree;

	/**
	 * Creates a new BTree with a new starting root
	 * @param maxOrder
	 */
	public BTree(int degree) {
        this.degree = degree;
		BTreeNode rootNode = new BTreeNode(degree, true);
		this.root = rootNode;
	}
	
	/**
	 * Getter for the root of the tree
	 * @return
	 */
	public BTreeNode getRoot() {
		return this.root;
	}
	
	public TreeObject BTreeSearch(BTreeNode currNode, long key){
		int index = 0;
		while((index < currNode.getNumKeys()) && (key > currNode.checkKey(index))) {
			index++;
		}
		if((index < currNode.getNumKeys()) && (key == currNode.checkKey(index))) {
			currNode.retrieveTreeObject(index).increaseFrequency();
			return currNode.retrieveTreeObject(index);
		} 
		if (currNode.isLeaf) {
			return null;
		} 
			
		return BTreeSearch(currNode.children[index], key);
		
	}
	
	public void BTreeInsert(long k) {
		TreeObject newObj = new TreeObject(k);
		BTreeNode currNode = this.root;
		
		if(currNode.numKeys == 2 * degree - 1) {
			BTreeNode s = new BTreeNode(degree, false);
			this.root = s;
			s.numKeys = 0;
			s.children[0] = currNode;
			BTreeSplitChild(s, 0);
			BTreeInsertNonFull(s,newObj);
		} else {
			BTreeInsertNonFull(currNode,newObj);
		}
	}
	
	private void BTreeInsertNonFull(BTreeNode currNode, TreeObject item) {
		int i = currNode.numKeys - 1;
		if(currNode.isLeaf) {
			while(i >= 0 && item.getlongKey() < currNode.keys[i].getlongKey()) {
				currNode.keys[i + 1] = currNode.keys[i];
				i = i - 1;
			}
			currNode.keys[i + 1] = item;
			currNode.numKeys = currNode.numKeys + 1;
		} else {
			while (i >= 0 && currNode.keys[i].getlongKey() > item.getlongKey()) {
				i--;
			}
			i = i + 1;
			if(currNode.children[i].numKeys  == 2 * degree - 1) {
				BTreeSplitChild(currNode, i);
				if(item.getlongKey() > currNode.keys[i].getlongKey()) {
					i = i + 1;
				}
			}
			BTreeInsertNonFull(currNode.children[i], item);
		}
	}
	
	private void BTreeSplitChild(BTreeNode currNode, int index) {
		BTreeNode y = currNode.children[index];
		BTreeNode z = new BTreeNode(degree,y.isLeaf);
		z.numKeys = degree - 1;
		
		for(int j = 0; j < degree - 1; j++) {
			z.keys[j] = y.keys[j + degree];
		}
		if(!y.isLeaf) {
			for(int j = 0; j < degree; j++) {
				z.children[j] = y.children[j + degree];
			}
		}
		y.numKeys = degree - 1;
		for(int j = currNode.numKeys; j >= index + 1; j--) {
			currNode.children[j + 1] = currNode.children[j];
		}
		currNode.children[index + 1] = z;
		
		for(int j = currNode.numKeys - 1; j >= index; j--) {
			currNode.keys[j + 1] = currNode.keys[j];
		}
		currNode.keys[index] = y.keys[degree - 1];
		
		currNode.numKeys = currNode.numKeys + 1;
	}
	
	public String dumpBTree(BTreeNode currNode) {
		int traverse;
		String bTreeString = "";
		for(traverse = 0; traverse < currNode.numKeys; traverse++) {
			if(!currNode.isLeaf) {
				bTreeString += dumpBTree(currNode.children[traverse]);
			}
			bTreeString += currNode.keys[traverse].toString();
		}
		
		if(!currNode.isLeaf) {
			bTreeString += dumpBTree(currNode.children[traverse]);
		}
		
		return bTreeString;
		
	}
	
	
	 /**
     * 
     * Private class BTreeNode
     * 
     * used in the class BTree. 
     */

    public class BTreeNode {

        //Instance Variables
        // t
        private int degree;
        //is this n a Leaf?
        public boolean isLeaf;
        //stores the keys in an array
        public TreeObject[] keys;
        //Number of keys in Node
        public int numKeys;
        //keeps track of references to the children nodes
        public BTreeNode[] children;


        // Constrcutor
        public BTreeNode(int degree, Boolean isLeaf){
            this.degree = degree;
            this.isLeaf = isLeaf;
            keys = new TreeObject[2 * degree - 1];
            numKeys = 0;
            children = new BTreeNode[2 * degree];
        }
        
        public int getNumKeys() {
        	return numKeys;
        }
        
        public void setNumKeys(int numKeys) {
        	this.numKeys = numKeys;
        }
        
        public boolean isItLeaf() {
        	return isLeaf;
        }
        
        public long checkKey(int index) {
        	return keys[index].getlongKey();
        }
        
        public TreeObject retrieveTreeObject(int index){
        	return keys[index];
        }
        
        public TreeObject setTreeObject(int index){
        	return keys[index];
        }
        
        public BTreeNode retrieveChild(int index){
        	return children[index];
        }
      
    }
	
    public class TreeObject {
    	private long key;
    	private int frequency;
    	
    	// Constructor
    	public TreeObject(long key) { 
    		this.key = key;
    		this.frequency = 1;
    	}
    	
    	// equality function
    	boolean equals(TreeObject otherItem) {
    		if(this.key == otherItem.getlongKey())
    			return true;
    		return false;
    	}
    	
    	public int getFrequency() {
    		return frequency;
    	}
    	
    	public void increaseFrequency() {
    		frequency++;
    	}
    	   	
    	public long getlongKey() {
    		return key;
    	}
    	
    	public String toString() {
    		String s = "";
    		s += frequency + " " + convertLongToSequence(key) + " ";
    		return s;
    	}
    	
    	private String convertLongToSequence(Long key) {
    		String GeneSeq = "";
    		long longValue = 0;
    		for(int i = 0; i < 3; i++) {
    			longValue = (key >> i * 2) & 3;
    			if((int)longValue == 0) {
    				GeneSeq += "A";
    			}
    			if((int)longValue == 3) {
    				GeneSeq += "T";
    			}
    			if((int)longValue == 1) {
    				GeneSeq += "C";
    			}
    			if((int)longValue == 2) {
    				GeneSeq += "G";
    			}
    		}
    		
    		return GeneSeq;
    	}
	}
}


   

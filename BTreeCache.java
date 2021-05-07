import java.util.*;

public class BTreeCache<BTreeNode> {
	private int size;
	private int hitCount;
	private int missCount;
	private LinkedList<BTreeNode> list;

	public BTreeCache(int sizeInput) {
		size = sizeInput;
		hitCount = 0;
		missCount = 0;
		list = new LinkedList<BTreeNode>();

}

public boolean addBTN(BTreeNode btn) {

    if (isNode(btn)) {
	    
        if (checkCache(btn)) {
		    list.remove(btn);
		    list.addfirst(btn);
		    hitCount++;
        }	
	    
        if (checkCache(btn) != true) {
		    missCount++;
		
		    if(cacheFull) {
			    list.removeLast();
			    list.addfirst(btn);
		    }

		    if(!cacheFull) {
			    list.addFirst(btn);
		    }
	    }
	        return true;
    }

    System.out.println("Unidentified object. Could not add to cache.");
    return false;
}

public boolean cacheFull() {
    if (list.size() == cacheSize()) {
	    return true;
    }
        return false;
}

public int getHitCount() {
	return hitCount;
}

public int cacheSize() {
	return size;
}

public int getMissCount() {
	return missCount;
}

public boolean checkCache(BTreeNode btn) {
	if(list.contains(btn)) {
		return true;
    }
    return false;	
}

public double getRatio() {
double hitRatio = ((double) getHitCount()) / (getHitCount() + getMissCount()); 
}

public boolean isNode(BTreeNode btn) {
	if(btn != null) {
	return true;
    }
    return false;
}

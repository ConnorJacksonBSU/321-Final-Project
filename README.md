# Bioinformatics

This program uses BTrees to sequence and store the human genome. 

## Contributing
- Connor Jackson
- Andrew Moorcroft
- Jackson Looney



## Contained Files:

- BTree.java : Class that implements a BTree data Structure. Has an inner class BTreeNode, that is utilized.
- BTreeCache.java : Cache implementation that stores BTreeNodes

- GeneBankCreateBTree : takes a Gene Bank file, and stores it in a BTree.

- GeneBankSearch.java : Uses the Gene Bank B Tree, and searches it for sequences of a given length.
## Usage

In Terminal:
Compiling:
```
javac GeneBankCreateBTree.java
javac GeneBankSearch.java
```
Using:
```
java GeneBankCreateBTree <0/1(no/with Cache)> <degree> <gbk file> <sequence length> <cache size> [<debug level>]

java GeneBankSearch <0/1(no/with Cache)> <btree file> <query file> <cache size > [<debug level>]
```




## BTree Disk Description


The root node is always kept in memory, and is only written to disk when the program ends , and gets read in when the program starts. The block size is 4096 bytes. The offset is 4 bytes. 


4096 = 4k + 4C +K(2t -1) + 2tF

C = K+1



## Notes

The gene sequence is stored as a string, and the sequence is able to be re-implemented into a BTree, via the BTree constructor.



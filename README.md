# Bioinformatics

This program uses BTrees to sequence and store the human genome by taking in Gene Bank files.

## Contributing
- Connor Jackson
- Andrew Moorcroft
- Jackson Looney



## Contained Files:

- BTree.java : Class that embodies a BTree data Structure. Has an inner class BTreeNode, Which holds key and child value. Inner Class TreeObject holds the
               actual keys.
- BTreeCache.java : Cache implementation that stores BTreeNodes

- GeneBankCreateBTree : Takes a Gene Bank file, and puts it into a B tree which is then deposited into a data file .

- GeneBankSearch.java : Uses the B Tree File, and searches it for sequences of a given length.
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


The root node is kept in memory, and is written to disk via a data file when the GeneBankCreateDataTree ends. GeneBankSearch Will find a created data file and then passes it to our BTree class which reimplements the file into a working BTree.

The block size is 4096 bytes. 


4096 = 4k + 4C +K(2t -1) + 2tF

C = K+1

As for the actual calculations, we couldn't find the offset for our specific BTree, also the exact bytes for each object +key, and the metadata.

## Notes

The gene sequence is stored as a string, and the sequence is able to be re-implemented into a BTree, via the BTree constructor.



# Goal of the project

This repository provides an *Algorithm & Data Structures* library in Java.

You may use it for competitive programming challenges or in your own application.


## Context

Although you should start with the classes made available by the Java standard such as *ArrayList*, *LinkedList*, *HashMap*, *TreeMap*, *PriorityQueue* etc., it may not always be sufficient.

You can always write a simple solution to the problem, but naive implementation takes too long and gets timed-out.

You need more advanced packages than what's available in the Java Runtime Environment. JRE lacks more exotic yet important constructs that perform ad-hoc queries efficiently.

This library is meant to provide complex implementations that solves the problem more efficiently than brute-force approach.

It normalizes recurrent patterns for re-usability purpose. It enables easy integration to be used as a black box. By skipping the low level implementation of the textbook algorithms, you can focus your efforts on the problem at hand.

Consult [CLRS](https://www.amazon.com/Introduction-Algorithms-3rd-MIT-Press/dp/0262033844) for more in-depth analysis of the building blocks.


# Examples

## Algorithms

### Depth First Search

Assuming a graph representation via a list of adjacency lists, you can perform ad-hoc DFS by implementing *node visitor*.

```Java
      // the adjacency list
      List<List<Integer>> adjacency = new ArrayList<>();
      // build the list
      ...

      // traverse the graph
      Traversal traversal = new AdjacencyListDFSTraversal(adjacency);
      traversal.traverse((current, parent, children) -> {
         LOG.info(String.format("Visiting node %d", current));
      });

```

## Data Structures

### Range Query

Suppose you need to run a query such as *min*, *max*, *sum*, *count* etc. multiple times over any ranges in an array, with potential updates in between.

If *Square Root decomposition* or *MO's algorithm* are not possible, you may need a more advanced data structure.

#### Sparse Table

This can be used on an array with no updates between queries,

```Java
        SparseTable<Integer> rangeSum = new SparseTable<Integer>(a) {
            public Integer neutralElement() {
                return 0;
            }

            public Integer query(Integer t1, Integer t2) {
                return t1 + t2;
            }
        };

        int sum = rangeSum.rangeQuery(l, r);
```

#### Binary Indexed Tree

This can be used for *sum*, *count* operations for a mix of read/write queries,

```Java
        AbstractBinaryIndexedTree<Integer> bit = new SumBinaryIndexedTree(10);
        bit.change(4, 1);
        bit.change(3, 1);
        bit.change(8, 1);
        assertEquals(3, bit.get(9));
```

#### Segment Tree

This can be used for *sum*, *count* and *min*, *max* as well for a mix of read/write queries,

```Java
        Integer[] a = new Integer[] { 0, 1, 2, 1, 3, 2 };
        AbstractSegmentTree<Integer> segmentTree = new AbstractSegmentTree<>(a) {
            public Integer neutralElement() {
                return Integer.MAX_VALUE;
            }

            public Integer query(Integer x, Integer y) {
                return Math.min(x, y);
            }
        };
        assertEquals(1, segmentTree.rangeQuery(1, 5));
```

## Arithmetics

To compute integer operations in **Z/pZ** field, usually `p` is a large prime number.

### Modular integer

For example, compute the inverse element:

```Java
        int p = 7;
        ModularArithmetics modularArithmetics = new ModularArithmetics(p);
        int[] inverses = IntStream.range(1, p)
                .map(modularArithmetics::inversePrime)
                .toArray();
        assertArrayEquals(new int[] { 1, 4, 5, 2, 3, 6 }, inverses);
```

For example, compute `m^e [p]` via *logarithmic exponentiation*, where `m` is a square matrix and `e` an integer,

```Java
        ModularAlgebra modularAlgebra = new ModularAlgebra(p);
        int[][] exponent = modularAlgebra.exponent(m, e);
```

# Usage

## Download

The jar is available for [download](https://github.com/alexis779/Algo/packages/1693117) via Github Packages.

## Build

[Gradle](https://docs.gradle.org/current/userguide/userguide.html) is the build system. It compiles the source, runs unit tests and packages the code into a jar file.

```
$ gradle build
```
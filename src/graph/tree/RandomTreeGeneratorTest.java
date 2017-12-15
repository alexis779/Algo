package graph.tree;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class RandomTreeGeneratorTest {
    @Test
    public void random() {
        RandomTreeGenerator randomTreeGenerator = new RandomTreeGenerator();
        int n = 10;
        Tree tree = randomTreeGenerator.generate(n);
        assertEquals("Number of nodes in the tree", n, tree.getNodes().size());
        tree.print();
    }
}

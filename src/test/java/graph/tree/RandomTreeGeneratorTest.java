package graph.tree;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class RandomTreeGeneratorTest {
    @Test
    public void random() {
        RandomTreeGenerator randomTreeGenerator = new RandomTreeGenerator();
        int n = 10;
        Tree tree = randomTreeGenerator.generate(n);
        assertEquals(n, tree.getNodes().size(), "Number of nodes in the tree");
        tree.print();
    }
}

package org.tech.vineyard.graph.mst;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.tech.vineyard.graph.DotParser;
import org.tech.vineyard.graph.Edge;

import java.io.File;
import java.util.List;

public class MinimumSpanningTreeTest {

    private List<List<Edge>> adjacency;

    @BeforeEach
    public void loadGraph() throws Exception {
        String fileName = getClass()
                .getResource("../undirected_graph.dot")
                .getFile();
        File file = new File(fileName);
        DotParser dotParser = new DotParser(file);

        adjacency = dotParser.parseAdjacency();
    }

    @Test
    public void kruskal() {
        KruskalMST kruskalMST = new KruskalMST(adjacency);
        Assertions.assertEquals(39, kruskalMST.mstWeight(), "weight of a minimal spanning tree");
    }

    @Test
    public void prim() {
        PrimMST primMST = new PrimMST(adjacency);
        System.out.println(primMST.mstWeight(0));
    }

    @Test
    public void reverseDelete() {

    }

    @Test
    public void boruvka() {

    }
}

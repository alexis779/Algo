package org.tech.vineyard.graph;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

public class DotParserTest {

    @Test
    public void dot() throws Exception {
        String fileName = getClass()
                .getResource("undirected_graph.dot")
                .getFile();
        File file = new File(fileName);
        DotParser dotParser = new DotParser(file);
        List<List<Edge>> adjacency = dotParser.parseAdjacency();
        Assertions.assertEquals(new Edge(0, 1, 7), adjacency.get(0).get(0), "0 -- 1 [label=7]");
    }
}

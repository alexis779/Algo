package graph.tree;

import java.util.HashMap;
import java.util.Map;

public class SubTree extends Tree {
    /**
     * This maps the tree node index to the subTree node index.
     */
    Map<Integer, Integer> nodeIds = new HashMap<>();

    public void setNodeIds() {
        for (int i = 0; i < nodes.size(); i++) {
            nodeIds.put(nodes.get(i).getId(), i);
        }
    }

    protected int getNodeId(int nodeId) {
        return nodeIds.get(nodeId);
    }
}

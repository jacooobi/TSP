package put.poznan.Structures;

import java.util.ArrayList;

public class Graph extends ArrayList<Integer> {
    public static Graph fromNodes(Nodes nodes) {
        Graph newGraph = new Graph();

        for (Node node : nodes) {
            newGraph.add(node.get("id"));
        }

        return newGraph;
    }
}

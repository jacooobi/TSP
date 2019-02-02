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

    public int getNext(int i) {
        if (i + 1 == this.size())
            return this.get(0);
        else
            return this.get(i + 1);
    }
}

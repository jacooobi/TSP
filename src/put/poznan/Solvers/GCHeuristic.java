package put.poznan.Solvers;

import put.poznan.Structures.Graph;
import put.poznan.Structures.Node;
import put.poznan.Structures.Nodes;
import put.poznan.TSPMath;

public class GCHeuristic extends BaseHeuristic {
    public GCHeuristic(Nodes nodes, String name) {
        super(nodes, name);
    }

    @Override
    protected Nodes evaluateCandidates(Graph graph) {
        Nodes distances = new Nodes();

        for(int i=0;i<nodes.size(); i++) {
            if (usedNodes.contains(i)) continue;

            Node candidateNode = new Node();
            Node firstNode = nodes.get(graph.get(0));
            Node lastNode = nodes.get(graph.get(graph.size()-1));
            Node curNode = nodes.get(i);

            int curToFirst = TSPMath.distance(curNode, firstNode);
            int curToLast = TSPMath.distance(curNode, lastNode);
            int newCost = TSPMath.getCost(nodes, graph) + curToFirst + curToLast;

            candidateNode.put("id", curNode.get("id"));
            candidateNode.put("distance", newCost);

            distances.add(candidateNode);
        }

        return distances;
    }
}
